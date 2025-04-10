package salao.online.application.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.application.dtos.dtosDoAgendamento.FormaPagamentoEnumDTO;
import salao.online.application.dtos.dtosDoAgendamento.StatusAgendamentoEnumDTO;
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
                        LocalDate data,
                        StatusAgendamentoEnumDTO statusDTO,
                        FormaPagamentoEnumDTO formaPagamentoDTO) {

                StatusAgendamentoEnum status = null;
                if (statusDTO != null) {
                        status = StatusAgendamentoEnum.fromStatusAgendamento(statusDTO.getStatusAgendamento());
                }

                FormaPagamentoEnum formaPagamento = null;
                if (formaPagamentoDTO != null) {
                        formaPagamento = FormaPagamentoEnum.fromFormaPagamento(formaPagamentoDTO.getFormaPagamento());
                }

                List<Agendamento> agendamentos = agendamentoRepository.buscarAgendamentosDoProfissional(
                                idProfissional, data, status, formaPagamento);

                return agendamentoMapper.fromEntityListToDtoList(agendamentos);
        }

        @Override
        public List<AgendamentoDTO> buscarAgendamentosDoCliente(
                        UUID idCliente,
                        LocalDate data,
                        StatusAgendamentoEnumDTO statusDTO,
                        FormaPagamentoEnumDTO formaPagamentoDTO) {

                StatusAgendamentoEnum status = null;
                if (statusDTO != null) {
                        status = StatusAgendamentoEnum.fromStatusAgendamento(statusDTO.getStatusAgendamento());
                }

                FormaPagamentoEnum formaPagamento = null;
                if (formaPagamentoDTO != null) {
                        formaPagamento = FormaPagamentoEnum.fromFormaPagamento(formaPagamentoDTO.getFormaPagamento());
                }

                List<Agendamento> agendamentos = agendamentoRepository.buscarAgendamentosDoCliente(
                                idCliente, data, status, formaPagamento);

                return agendamentoMapper.fromEntityListToDtoList(agendamentos);
        }

}
