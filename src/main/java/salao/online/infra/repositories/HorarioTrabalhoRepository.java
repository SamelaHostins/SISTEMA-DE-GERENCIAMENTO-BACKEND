package salao.online.infra.repositories;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import salao.online.domain.entities.HorarioTrabalho;

public interface HorarioTrabalhoRepository extends PanacheRepositoryBase<HorarioTrabalho, UUID> {

}
