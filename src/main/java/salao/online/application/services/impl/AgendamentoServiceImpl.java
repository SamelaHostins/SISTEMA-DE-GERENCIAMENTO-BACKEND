package salao.online.application.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.application.dtos.dtosDoAgendamento.CriarAgendamentoPeloClienteDTO;
import salao.online.application.dtos.dtosDoAgendamento.CriarAgendamentoPeloProfissionalDTO;
import salao.online.application.mappers.AgendamentoMapper;
import salao.online.application.services.interfaces.AgendamentoService;
import salao.online.domain.entities.Agendamento;
import salao.online.domain.entities.Cliente;
import salao.online.domain.entities.Servico;
import salao.online.domain.enums.FormaPagamentoEnum;
import salao.online.domain.enums.StatusAgendamentoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.AgendamentoRepository;
import salao.online.infra.repositories.ClienteRepository;
import salao.online.infra.repositories.ProfissionalRepository;
import salao.online.infra.repositories.ServicoRepository;

@ApplicationScoped
public class AgendamentoServiceImpl implements AgendamentoService {

        @Inject
        AgendamentoRepository agendamentoRepository;

        @Inject
        AgendamentoMapper agendamentoMapper;

        @Inject
        ServicoRepository servicoRepository;

        @Inject
        ClienteRepository clienteRepository;

        @Inject
        ProfissionalRepository profissionalRepository;

        @Override
        public CriarAgendamentoPeloClienteDTO agendarPeloCliente(CriarAgendamentoPeloClienteDTO agendamentoDTO) {
                Cliente cliente = clienteRepository.findById(agendamentoDTO.getIdCliente());
                Servico servico = servicoRepository.findById(agendamentoDTO.getIdServico());

                if (cliente == null || servico == null) {
                        throw new WebApplicationException("Cliente ou serviço não encontrado", 400);
                }

                Agendamento agendamento = agendamentoMapper.fromCriarToEntity(agendamentoDTO);

                // Sobrescreve com entidades reais buscadas do banco
                agendamento.setCliente(cliente);
                agendamento.setServico(servico);

                // Verifica sobreposição
                boolean haSobreposicao = agendamentoRepository.existeSobreposicao(
                                servico.getProfissional().getIdProfissional(),
                                agendamento.getDataAgendamento(),
                                agendamento.getHoraAgendamento(),
                                servico.getTempo());

                if (haSobreposicao) {
                        throw new WebApplicationException(
                                        "O horário selecionado está em conflito com outro agendamento.", 409);
                }

                agendamento.setStatusAgendamento(StatusAgendamentoEnum.AGENDADO); // Defina um status padrão, se
                                                                                  // necessário

                agendamentoRepository.persistAndFlush(agendamento);

                return agendamentoMapper.fromEntityToCriarDto(agendamento);
        }

        @Override
        public CriarAgendamentoPeloProfissionalDTO agendarComoProfissional(UUID profissionalId,
                        CriarAgendamentoPeloProfissionalDTO dto)
                        throws ValidacaoException {
                Servico servico = servicoRepository.findById(dto.getIdServico());

                if (servico == null) {
                        throw new WebApplicationException("Serviço não encontrado", 400);
                }

                Cliente cliente = null;
                if (dto.getIdCliente() != null) {
                        cliente = clienteRepository.findById(dto.getIdCliente());
                        if (cliente == null) {
                                throw new WebApplicationException("Cliente informado não existe", 400);
                        }
                }

                boolean haSobreposicao = agendamentoRepository.existeSobreposicao(
                                profissionalId,
                                dto.getDataAgendamento(),
                                dto.getHoraAgendamento(),
                                servico.getTempo());

                if (haSobreposicao) {
                        throw new ValidacaoException("Horário indisponível para este profissional");
                }

                // Usa o mapper para instanciar a entidade
                Agendamento agendamento = agendamentoMapper.fromCriarProfissionalToEntity(dto);
                agendamento.setCliente(cliente);
                agendamento.setServico(servico);
                agendamento.setStatusAgendamento(StatusAgendamentoEnum.AGENDADO);
                agendamento.setFormaPagamento(FormaPagamentoEnum.DINHEIRO);

                agendamentoRepository.persistAndFlush(agendamento);

                return agendamentoMapper.fromEntityToCriarProfissional(agendamento);
        }

        @Override
        public List<AgendamentoDTO> buscarAgendamentosDoProfissional(
                        UUID idProfissional,
                        LocalDate dataInicio,
                        LocalDate dataFim,
                        Integer statusValor,
                        Integer formaPagamentoValor) {

                StatusAgendamentoEnum status = statusValor != null ? StatusAgendamentoEnum.values()[statusValor] : null;
                FormaPagamentoEnum formaPagamento = formaPagamentoValor != null
                                ? FormaPagamentoEnum.values()[formaPagamentoValor]
                                : null;

                List<Agendamento> agendamentos = agendamentoRepository.buscarAgendamentosDoProfissional(
                                idProfissional, dataInicio, dataFim, status, formaPagamento);

                return agendamentoMapper.fromEntityListToDtoList(agendamentos);
        }

        @Override
        public List<AgendamentoDTO> buscarAgendamentosDoCliente(
                        UUID idCliente,
                        LocalDate dataInicio,
                        LocalDate dataFim,
                        Integer statusValor,
                        Integer formaPagamentoValor) {

                StatusAgendamentoEnum status = statusValor != null ? StatusAgendamentoEnum.values()[statusValor] : null;
                FormaPagamentoEnum formaPagamento = formaPagamentoValor != null
                                ? FormaPagamentoEnum.values()[formaPagamentoValor]
                                : null;

                List<Agendamento> agendamentos = agendamentoRepository.buscarAgendamentosDoCliente(
                                idCliente, dataInicio, dataFim, status, formaPagamento);

                return agendamentoMapper.fromEntityListToDtoList(agendamentos);
        }

        @Override
        @Transactional
        public void cancelarAgendamento(UUID idAgendamento, UUID idUsuario, boolean isProfissional)
                        throws ValidacaoException {
                Agendamento agendamento = agendamentoRepository.findById(idAgendamento);

                if (agendamento == null) {
                        throw new ValidacaoException("Agendamento não encontrado");
                }

                UUID dono = isProfissional
                                ? agendamento.getServico().getProfissional().getIdProfissional()
                                : agendamento.getCliente().getIdCliente();

                if (!dono.equals(idUsuario)) {
                        throw new ValidacaoException("Você não tem permissão para cancelar este agendamento");
                }

                agendamentoRepository.atualizarStatus(idAgendamento, StatusAgendamentoEnum.CANCELADO);
        }
}
