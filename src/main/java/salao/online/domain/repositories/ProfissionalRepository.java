package salao.online.domain.repositories;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import salao.online.domain.entities.Profissional;

public interface ProfissionalRepository extends PanacheRepositoryBase<Profissional, UUID> {
    
}
