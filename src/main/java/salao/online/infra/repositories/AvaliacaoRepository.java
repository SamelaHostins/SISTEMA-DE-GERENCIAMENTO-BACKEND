package salao.online.infra.repositories;

import java.util.Optional;
import java.util.UUID;

import salao.online.domain.entities.Avaliacao;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public interface AvaliacaoRepository extends PanacheRepositoryBase<Avaliacao, UUID> {
    
    public Optional<Avaliacao> buscarAvaliacaoClienteServico(UUID idCliente, UUID idServico);
}
