package salao.online.infra;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import salao.online.domain.entities.Estoque;
import salao.online.domain.entities.Produto;
import salao.online.domain.repositories.EstoqueRepository;

@ApplicationScoped
public class EstoqueRepositoryImpl implements EstoqueRepository {

    @Override
    public List<Estoque> buscarEstoques() {
        List<Estoque> estoques = listAll();
        return estoques.stream()
                .sorted(Comparator.comparing(Estoque::getNome))
                .collect(Collectors.toList());
    }

    @Override
    public List<Produto> buscarProdutosDoEstoque(UUID idEstoque) {
        Estoque estoque = findByIdOptional(idEstoque).orElse(null);
        if (estoque != null) {
            return estoque.getProdutos().stream()
                    .sorted(Comparator.comparing(Produto::getNome))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}
