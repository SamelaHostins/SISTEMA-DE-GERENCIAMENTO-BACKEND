package salao.online.infra.repositories;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import salao.online.domain.entities.Agendamento;
import salao.online.domain.enums.FormaPagamentoEnum;
import salao.online.domain.enums.StatusAgendamentoEnum;

public interface AgendamentoRepository extends PanacheRepositoryBase<Agendamento, UUID> {

        List<Agendamento> buscarAgendamentosDoProfissional(UUID idProfissional, LocalDate dataInicio, LocalDate dataFim,
                        StatusAgendamentoEnum status, FormaPagamentoEnum formaPagamento);

        List<Agendamento> buscarAgendamentosDoCliente(UUID idCliente, LocalDate dataInicio, LocalDate dataFim,
                        StatusAgendamentoEnum status, FormaPagamentoEnum formaPagamento);

        public boolean existeSobreposicao(
                        UUID profissionalId,
                        LocalDate data,
                        LocalTime inicioProposto,
                        Duration duracaoProposta);

        public List<Agendamento> listarPorCliente(UUID clienteId);

        public List<Agendamento> listarPorProfissional(UUID profissionalId);

        public List<Agendamento> buscarPorProfissionalEData(UUID idProfissional, LocalDate data);

            
}
