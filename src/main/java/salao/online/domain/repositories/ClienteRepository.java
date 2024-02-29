package salao.online.domain.repositories;

import java.util.List;
import java.util.UUID;

import salao.online.domain.entities.Avaliacao;
import salao.online.domain.entities.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public interface ClienteRepository extends PanacheRepositoryBase<Cliente, UUID> {
    
    public List<Avaliacao> buscarTodasAvaliacoesDoCliente(UUID idCliente);
}
