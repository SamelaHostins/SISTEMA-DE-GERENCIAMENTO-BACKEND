package salao.online.application.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;

public interface AgendamentoService {

        List<AgendamentoDTO> buscarAgendamentosDoProfissional(UUID idProfissional,
                        LocalDate dataInicio,
                        LocalDate dataFim,
                        Integer statusValor,
                        Integer formaPagamentoValor);

        List<AgendamentoDTO> buscarAgendamentosDoCliente(UUID idCliente,
                        LocalDate dataInicio,
                        LocalDate dataFim,
                        Integer statusValor,
                        Integer formaPagamentoValor);
}
