package salao.online.infra.repositoriesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import salao.online.domain.entities.Agendamento;
import salao.online.domain.enums.FormaPagamentoEnum;
import salao.online.domain.enums.StatusAgendamentoEnum;
import salao.online.infra.repositories.AgendamentoRepository;

@ApplicationScoped
public class AgendamentoRepositoryImpl implements AgendamentoRepository {

    @Override
    public List<Agendamento> buscarAgendamentosDoProfissional(UUID idProfissional, LocalDate dataInicio,
            LocalDate dataFim, StatusAgendamentoEnum status, FormaPagamentoEnum formaPagamento) {

        StringBuilder query = new StringBuilder("servico.profissional.idProfissional = ?1");
        List<Object> params = new ArrayList<>();
        params.add(idProfissional);

        if (dataInicio != null && dataFim != null) {
            query.append(" and dataAgendamento BETWEEN ?2 AND ?3");
            params.add(dataInicio);
            params.add(dataFim);
        } else if (dataInicio != null) {
            query.append(" and dataAgendamento >= ?2");
            params.add(dataInicio);
        } else if (dataFim != null) {
            query.append(" and dataAgendamento <= ?2");
            params.add(dataFim);
        }

        if (status != null) {
            query.append(" and statusAgendamento = ?" + (params.size() + 1));
            params.add(status);
        }

        if (formaPagamento != null) {
            query.append(" and formaPagamento = ?" + (params.size() + 1));
            params.add(formaPagamento);
        }

        return find(query.toString(), params.toArray()).list();
    }

    @Override
    public List<Agendamento> buscarAgendamentosDoCliente(UUID idCliente, LocalDate dataInicio,
            LocalDate dataFim, StatusAgendamentoEnum status, FormaPagamentoEnum formaPagamento) {

        StringBuilder query = new StringBuilder("cliente.idCliente = ?1");
        List<Object> params = new ArrayList<>();
        params.add(idCliente);

        if (dataInicio != null && dataFim != null) {
            query.append(" and dataAgendamento BETWEEN ?2 AND ?3");
            params.add(dataInicio);
            params.add(dataFim);
        } else if (dataInicio != null) {
            query.append(" and dataAgendamento >= ?2");
            params.add(dataInicio);
        } else if (dataFim != null) {
            query.append(" and dataAgendamento <= ?2");
            params.add(dataFim);
        }

        if (status != null) {
            query.append(" and statusAgendamento = ?" + (params.size() + 1));
            params.add(status);
        }

        if (formaPagamento != null) {
            query.append(" and formaPagamento = ?" + (params.size() + 1));
            params.add(formaPagamento);
        }

        return find(query.toString(), params.toArray()).list();
    }
}
