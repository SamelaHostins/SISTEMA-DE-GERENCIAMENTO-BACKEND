package salao.online.infra.repositoriesImpl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import salao.online.domain.entities.Agendamento;
import salao.online.domain.enums.FormaPagamentoEnum;
import salao.online.domain.enums.StatusAgendamentoEnum;
import salao.online.infra.repositories.AgendamentoRepository;

@ApplicationScoped
public class AgendamentoRepositoryImpl implements AgendamentoRepository {

    @Override
    public List<Agendamento> buscarAgendamentosDoProfissional(
            UUID idProfissional,
            LocalDate dataInicio,
            LocalDate dataFim,
            StatusAgendamentoEnum status,
            FormaPagamentoEnum formaPagamento) {

        StringBuilder q = new StringBuilder("servico.profissional.idProfissional = :pid");
        Parameters params = Parameters.with("pid", idProfissional);

        if (dataInicio != null) {
            q.append(" and dataAgendamento >= :start");
            params = params.and("start", dataInicio);
        }
        if (dataFim != null) {
            q.append(" and dataAgendamento <= :end");
            params = params.and("end", dataFim);
        }
        if (status != null) {
            q.append(" and statusAgendamento = :st");
            params = params.and("st", status);
        }
        if (formaPagamento != null) {
            q.append(" and formaPagamento = :fp");
            params = params.and("fp", formaPagamento);
        }

        return find(q.toString(), params).list();
    }

    @Override
    public List<Agendamento> buscarAgendamentosDoCliente(
            UUID idCliente,
            LocalDate dataInicio,
            LocalDate dataFim,
            StatusAgendamentoEnum status,
            FormaPagamentoEnum formaPagamento) {

        StringBuilder q = new StringBuilder("cliente.idCliente = :cid");
        Parameters params = Parameters.with("cid", idCliente);

        if (dataInicio != null) {
            q.append(" and dataAgendamento >= :start");
            params = params.and("start", dataInicio);
        }
        if (dataFim != null) {
            q.append(" and dataAgendamento <= :end");
            params = params.and("end", dataFim);
        }
        if (status != null) {
            q.append(" and statusAgendamento = :st");
            params = params.and("st", status);
        }
        if (formaPagamento != null) {
            q.append(" and formaPagamento = :fp");
            params = params.and("fp", formaPagamento);
        }

        return find(q.toString(), params).list();
    }

    @Override
    public boolean existeSobreposicao(
            UUID profissionalId,
            LocalDate data,
            LocalTime inicioProposto,
            Duration duracaoProposta) {

        LocalTime fimProposto = inicioProposto.plus(duracaoProposta);
        long count = find(
                "servico.profissional.idProfissional = :pid"
                        + " and dataAgendamento = :date"
                        + " and statusAgendamento <> :cancelado"
                        + " and (horaAgendamento < :fim and (horaAgendamento + servico.tempo) > :inicio)",
                Parameters.with("pid", profissionalId)
                        .and("date", data)
                        .and("cancelado", StatusAgendamentoEnum.CANCELADO)
                        .and("inicio", inicioProposto)
                        .and("fim", fimProposto))
                .count();

        return count > 0;
    }

    @Override
    public List<Agendamento> listarPorCliente(UUID clienteId) {
        return list("cliente.idCliente", clienteId);
    }

    @Override
    public List<Agendamento> listarPorProfissional(UUID profissionalId) {
        return list("servico.profissional.idProfissional", profissionalId);
    }

    @Override
    public List<Agendamento> buscarPorProfissionalEData(UUID idProfissional, LocalDate data) {
        return find("servico.profissional.idProfissional = ?1 and dataAgendamento = ?2", idProfissional, data).list();
    }

}
