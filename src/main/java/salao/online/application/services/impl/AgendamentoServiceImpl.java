package salao.online.application.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.application.mappers.AgendamentoMapper;
import salao.online.application.services.interfaces.AgendamentoService;
import salao.online.domain.entities.Agendamento;
import salao.online.domain.enums.FormaPagamentoEnum;
import salao.online.domain.enums.StatusAgendamentoEnum;
import salao.online.infra.repositories.AgendamentoRepository;

@ApplicationScoped
public class AgendamentoServiceImpl implements AgendamentoService {

        @Inject
        AgendamentoRepository agendamentoRepository;

        @Inject
        AgendamentoMapper agendamentoMapper;

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
