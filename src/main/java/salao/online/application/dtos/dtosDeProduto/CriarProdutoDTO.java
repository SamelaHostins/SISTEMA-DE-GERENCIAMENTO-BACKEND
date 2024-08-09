package salao.online.application.dtos.dtosDeProduto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriarProdutoDTO {
    
    private String nome;
    private LocalDate dtEntradaProduto;
    private LocalDate dtValidadeProduto;
    private double valor;
}
