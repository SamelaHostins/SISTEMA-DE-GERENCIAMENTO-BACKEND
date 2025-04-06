package salao.online.infra.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import salao.online.domain.entities.Agendamento;
import salao.online.domain.enums.FormaPagamentoEnum;
import salao.online.domain.enums.StatusAgendamentoEnum;

public interface AgendamentoRepository extends PanacheRepositoryBase<Agendamento, UUID> {

    List<Agendamento> buscarAgendamentosDoProfissional(UUID idProfissional, LocalDate data,
            StatusAgendamentoEnum status, FormaPagamentoEnum formaPagamento);

    List<Agendamento> buscarAgendamentosDoCliente(UUID idCliente, LocalDate data,
            StatusAgendamentoEnum status, FormaPagamentoEnum formaPagamento);

}
