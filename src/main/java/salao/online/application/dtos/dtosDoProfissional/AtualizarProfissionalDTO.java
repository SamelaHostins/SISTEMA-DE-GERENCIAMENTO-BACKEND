package salao.online.application.dtos.dtosDoProfissional;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.application.dtos.EnderecoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AtualizarProfissionalDTO {

    private UUID idProfissional; 
    private String instagram;
    private String profissao;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String senha;
    private String documento;
    private EnderecoDTO endereco; 
}
