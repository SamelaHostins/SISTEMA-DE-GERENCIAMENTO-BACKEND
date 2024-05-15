package salao.online.infra.repositories;

import java.util.Optional;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import salao.online.domain.entities.Profissional;

public interface ProfissionalRepository extends PanacheRepositoryBase<Profissional, UUID> {

    public Optional<Profissional> deletarCadastroDoProfissional(UUID idProfissional);
    
}
