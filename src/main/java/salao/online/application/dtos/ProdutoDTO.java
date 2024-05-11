package salao.online.application.dtos;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoDTO {

    private UUID idProduto;
    private String nome;
    private LocalDate dtEntradaProduto;
    private LocalDate dtValidadeProduto;
    private double valor;
    private UUID idEstoque;
}
