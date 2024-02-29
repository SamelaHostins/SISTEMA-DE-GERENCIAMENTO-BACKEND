package salao.online.domain.repositories;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import salao.online.domain.entities.Avaliacao;
import salao.online.domain.entities.Servico;

public interface ServicoRepository extends PanacheRepositoryBase<Servico, UUID> {

    public List<Servico> buscarServicos();

    public List<Avaliacao> buscarAvaliacoesDoServico(UUID idServico);
}
