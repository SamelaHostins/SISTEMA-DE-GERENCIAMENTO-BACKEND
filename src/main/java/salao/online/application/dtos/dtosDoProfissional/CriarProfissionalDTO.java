package salao.online.application.dtos.dtosDoProfissional;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.application.dtos.dtosDeEndereco.EnderecoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriarProfissionalDTO {

    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento; 
    private String email;
    private String telefone;
    private String instagram;
    private String profissao;
    private String senha;
    private String documento;
    private EnderecoDTO endereco; 
}
