package salao.online.application.dtos.dtosDoProfissional;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AtualizarProfissionalDTO {

    private UUID idProfissional; 
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String profissao;
    private String instagram;
    private String senha;
}
