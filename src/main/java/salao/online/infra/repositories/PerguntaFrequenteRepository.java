package salao.online.infra.repositories;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import salao.online.domain.entities.PerguntaFrequente;

public interface PerguntaFrequenteRepository extends PanacheRepositoryBase<PerguntaFrequente, UUID> {

    public List<PerguntaFrequente> buscarPorProfissional(UUID idProfissional);
}
