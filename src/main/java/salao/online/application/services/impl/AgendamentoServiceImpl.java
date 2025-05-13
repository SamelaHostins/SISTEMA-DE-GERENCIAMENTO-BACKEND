package salao.online.application.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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

        @Override
        public Agendamento agendarComoCliente(UUID clienteId,
                        CriarAgendamentoPeloClienteDTO dto)
                        throws ValidacaoException {
                Servico servico = servicoRepository.findById(dto.getIdServico());

                Cliente cliente = clienteRepository.findById(clienteId);

                int codigo = dto.getFormaPagamento().getCodigo();
                FormaPagamentoEnum forma = FormaPagamentoEnum.fromFormaPagamento(codigo);

                if (agendamentoRepository.existeSobreposicao(
                                servico.getProfissional().getIdProfissional(),
                                dto.getDataAgendamento(),
                                dto.getHoraAgendamento(),
                                servico.getTempo())) {
                        throw new ValidacaoException("Horário indisponível para este profissional");
                }

                Agendamento ag = new Agendamento(
                                dto.getDataAgendamento(),
                                dto.getHoraAgendamento(),
                                StatusAgendamentoEnum.AGENDADO,
                                forma,
                                cliente,
                                servico);
                agendamentoRepository.persist(ag);
                return ag;
        }

        @Override
        public Agendamento agendarComoProfissional(UUID profissionalId,
                        CriarAgendamentoPeloProfissionalDTO dto)
                        throws ValidacaoException {
                Servico servico = servicoRepository.findById(dto.getIdServico());

                if (!servico.getProfissional()
                                .getIdProfissional()
                                .equals(profissionalId)) {
                        throw new ValidacaoException("Você não tem permissão para agendar esse serviço.");
                }

                Cliente cliente = null;
                if (dto.getIdCliente() != null) {
                        cliente = clienteRepository.findById(dto.getIdCliente());
                }

                if (agendamentoRepository.existeSobreposicao(
                                profissionalId,
                                dto.getDataAgendamento(),
                                dto.getHoraAgendamento(),
                                servico.getTempo())) {
                        throw new ValidacaoException("Horário indisponível para este profissional");
                }

                Agendamento ag = new Agendamento(
                                dto.getDataAgendamento(),
                                dto.getHoraAgendamento(),
                                StatusAgendamentoEnum.AGENDADO,
                                FormaPagamentoEnum.DINHEIRO, 
                                cliente,
                                servico);

                agendamentoRepository.persist(ag);
                return ag;
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
}
