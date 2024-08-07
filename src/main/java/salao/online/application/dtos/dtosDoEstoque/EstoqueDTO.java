package salao.online.application.dtos.dtosDoEstoque;

import java.util.UUID;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.application.dtos.dtosDeProduto.ProdutoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EstoqueDTO {
    
    private UUID idEstoque;
    private String nome;
    private int qtdDeProdutos;
    private UUID idProfissional;
    private List<ProdutoDTO> produtos;
}
