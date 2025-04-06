package salao.online.application.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.domain.enums.FormaPagamentoEnum;
import salao.online.domain.enums.StatusAgendamentoEnum;

public interface AgendamentoService {

    List<AgendamentoDTO> buscarAgendamentosDoProfissional(UUID idProfissional, LocalDate data,
            StatusAgendamentoEnum status, FormaPagamentoEnum formaPagamento);

    List<AgendamentoDTO> buscarAgendamentosDoCliente(UUID idCliente, LocalDate data,
            StatusAgendamentoEnum status, FormaPagamentoEnum formaPagamento);
}
