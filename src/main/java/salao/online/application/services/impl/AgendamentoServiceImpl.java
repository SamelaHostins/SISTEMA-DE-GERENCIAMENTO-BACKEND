package salao.online.application.services.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
import salao.online.domain.entities.HorarioTrabalho;
import salao.online.domain.entities.Profissional;
import salao.online.domain.entities.Servico;
import salao.online.domain.enums.DiaSemanaEnum;
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
        public List<LocalTime> buscarHorariosDisponiveis(UUID idProfissional, LocalDate data) {
                Profissional profissional = profissionalRepository.findById(idProfissional);
                if (profissional == null) {
                        throw new WebApplicationException("Profissional não encontrado", 404);
                }

                // Descobre o dia da semana (0 = DOMINGO, 1 = SEGUNDA...)
                DiaSemanaEnum dia = DiaSemanaEnum.values()[data.getDayOfWeek().getValue() % 7];

                // Filtra faixas cadastradas pro dia
                List<HorarioTrabalho> faixas = profissional.getHorariosTrabalho().stream()
                                .filter(h -> h.getDiaSemana() == dia)
                                .toList();

                if (faixas.isEmpty())
                        return List.of(); // Sem expediente hoje? Então nem tenta.

                // Puxa os agendamentos já existentes do dia
                List<Agendamento> agendamentos = agendamentoRepository
                                .buscarPorProfissionalEData(idProfissional, data);

                List<LocalTime> horariosDisponiveis = new ArrayList<>();

                for (HorarioTrabalho faixa : faixas) {
                        LocalTime hora = faixa.getHoraInicio();

                        while (!hora.plusMinutes(30).isAfter(faixa.getHoraFim())) {
                                final LocalTime finalHora = hora; // Agora sim, sem treta com a lambda!

                                boolean sobrepoe = agendamentos.stream().anyMatch(ag -> {
                                        LocalTime inicioAg = ag.getHoraAgendamento();
                                        LocalTime fimAg = inicioAg.plus(ag.getServico().getTempo());
                                        return !(finalHora.plusMinutes(30).isBefore(inicioAg)
                                                        || finalHora.isAfter(fimAg));
                                });

                                if (!sobrepoe) {
                                        horariosDisponiveis.add(hora);
                                }

                                hora = hora.plusMinutes(30); // Bora pro próximo bloco
                        }
                }

                return horariosDisponiveis;
        }

        @Override
        @Transactional
        public void cancelarAgendamento(UUID idAgendamento, UUID idUsuario, boolean isProfissional) {
                Agendamento agendamento = agendamentoRepository.findById(idAgendamento);

                if (agendamento == null) {
                        throw new WebApplicationException("Agendamento não encontrado", 404);
                }

                UUID dono = isProfissional
                                ? agendamento.getServico().getProfissional().getIdProfissional()
                                : agendamento.getCliente().getIdCliente();

                if (!dono.equals(idUsuario)) {
                        throw new WebApplicationException("Acesso negado", 403);
                }

                agendamento.setStatusAgendamento(StatusAgendamentoEnum.CANCELADO);
                agendamentoRepository.persist(agendamento);
        }

}
