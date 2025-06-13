package salao.online.infra.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import salao.online.domain.entities.Avaliacao;
import salao.online.domain.entities.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public interface ClienteRepository extends PanacheRepositoryBase<Cliente, UUID> {

    public List<Avaliacao> buscarTodasAvaliacoesDoCliente(UUID idCliente);

    public Optional<Cliente> deletarCadastroDeCliente(UUID idCliente);

    public List<Cliente> buscarClientes();

    public Optional<Cliente> buscarClientePorId(UUID idCliente);

    public Optional<Cliente> buscarPeloEmail(String email);

    public boolean existePorEmail(String email);
    
    public boolean existePorDocumento(String documento);  
}
