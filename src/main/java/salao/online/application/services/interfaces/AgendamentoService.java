package salao.online.application.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.application.dtos.dtosDoAgendamento.FormaPagamentoEnumDTO;
import salao.online.application.dtos.dtosDoAgendamento.StatusAgendamentoEnumDTO;

public interface AgendamentoService {

        List<AgendamentoDTO> buscarAgendamentosDoProfissional(UUID idProfissional, LocalDate data,
                        StatusAgendamentoEnumDTO status, FormaPagamentoEnumDTO formaPagamento);

        List<AgendamentoDTO> buscarAgendamentosDoCliente(UUID idCliente, LocalDate data,
                        StatusAgendamentoEnumDTO status, FormaPagamentoEnumDTO formaPagamento);
}
