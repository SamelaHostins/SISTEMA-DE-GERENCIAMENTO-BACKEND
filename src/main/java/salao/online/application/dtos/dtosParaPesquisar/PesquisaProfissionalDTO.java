package salao.online.application.dtos.dtosParaPesquisar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PesquisaProfissionalDTO {
    
    private UUID idProfissional;
    private String usuario;
    private String nome;
    private String profissao;
}
