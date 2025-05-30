package salao.online.infra.repositoriesImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import salao.online.domain.entities.Produto;
import salao.online.infra.repositories.ProdutoRepository;

@ApplicationScoped
public class ProdutoRepositoryImpl implements ProdutoRepository {

    @Override
    public List<Produto> buscarProdutos() {
        List<Produto> produtos = listAll();
        return produtos.stream()
                .sorted(Comparator.comparing(Produto::getNome))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Produto> deletarProduto(UUID idProduto) {
        Optional<Produto> produtoOptional = findByIdOptional(idProduto);
        produtoOptional.ifPresent(produto -> {
            delete(produto);
            flush();
        });
        return produtoOptional;
    }    
}
