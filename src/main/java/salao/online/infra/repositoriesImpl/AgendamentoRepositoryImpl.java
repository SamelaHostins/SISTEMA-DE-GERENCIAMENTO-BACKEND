package salao.online.infra.repositoriesImpl;

import java.time.LocalDate;
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
    public List<Agendamento> buscarAgendamentosDoProfissional(UUID idProfissional, LocalDate data,
            StatusAgendamentoEnum status, FormaPagamentoEnum formaPagamento) {

        return find("servico.profissional.idProfissional = ?1"
                + (data != null ? " and dataAgendamento = ?2" : "")
                + (status != null ? " and statusAgendamento = ?3" : "")
                + (formaPagamento != null ? " and formaPagamento = ?4" : ""),
                buildParams(idProfissional, data, status, formaPagamento)).list();
    }

    @Override
    public List<Agendamento> buscarAgendamentosDoCliente(UUID idCliente, LocalDate data,
            StatusAgendamentoEnum status, FormaPagamentoEnum formaPagamento) {

        return find("cliente.idCliente = ?1"
                + (data != null ? " and dataAgendamento = ?2" : "")
                + (status != null ? " and statusAgendamento = ?3" : "")
                + (formaPagamento != null ? " and formaPagamento = ?4" : ""),
                buildParams(idCliente, data, status, formaPagamento)).list();
    }

    private Object[] buildParams(UUID id, LocalDate data, StatusAgendamentoEnum status, FormaPagamentoEnum pagamento) {
        return new Object[] {
                id,
                data != null ? data : null,
                status != null ? status : null,
                pagamento != null ? pagamento : null
        };
    }
}