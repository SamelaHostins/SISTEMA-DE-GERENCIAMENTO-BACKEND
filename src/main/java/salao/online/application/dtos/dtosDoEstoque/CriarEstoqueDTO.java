package salao.online.application.dtos.dtosDoEstoque;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriarEstoqueDTO {
    
    private String nome;
    private UUID idProfissional;
}
