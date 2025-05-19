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
    private String usuario;
    private String sobrenome;
    private String email;
    private String telefone;
    private int profissao;
    private String descricaoDaProfissao;
    private String instagram;
}
