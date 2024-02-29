package salao.online.application.dtos;

import java.util.UUID;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EstoqueDTO {
    
    private UUID idEstoque;
    private String nome;
    private int qtdDeProdutos;
    private List<ProdutoDTO> produtos;
    private UUID idProfissional;
}
