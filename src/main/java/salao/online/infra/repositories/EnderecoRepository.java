package salao.online.infra.repositories;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import salao.online.domain.entities.Endereco;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepositoryBase<Endereco, UUID> {
}
