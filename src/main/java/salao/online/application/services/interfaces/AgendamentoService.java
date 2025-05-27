package salao.online.application.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.application.dtos.dtosDoAgendamento.CriarAgendamentoPeloClienteDTO;
import salao.online.application.dtos.dtosDoAgendamento.CriarAgendamentoPeloProfissionalDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface AgendamentoService {

        CriarAgendamentoPeloClienteDTO agendarPeloCliente(CriarAgendamentoPeloClienteDTO agendamentoDTO);

        CriarAgendamentoPeloProfissionalDTO agendarComoProfissional(UUID profissionalId,
                        CriarAgendamentoPeloProfissionalDTO dto)
                        throws ValidacaoException;

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

        void cancelarAgendamento(UUID idAgendamento, UUID idUsuario, boolean isProfissional) throws ValidacaoException;
}
