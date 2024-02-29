package salao.online.domain.repositories;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import salao.online.domain.entities.Estoque;
import salao.online.domain.entities.Produto;

public interface EstoqueRepository extends PanacheRepositoryBase<Estoque, UUID> {

    public List<Estoque> buscarEstoques();

    public List<Produto> buscarProdutosDoEstoque(UUID idEstoque);
}
