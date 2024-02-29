package salao.online.domain.repositories;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import salao.online.domain.entities.Produto;

public interface ProdutoRepository extends PanacheRepositoryBase<Produto, UUID> {

    public List<Produto> buscarProdutos();
}
