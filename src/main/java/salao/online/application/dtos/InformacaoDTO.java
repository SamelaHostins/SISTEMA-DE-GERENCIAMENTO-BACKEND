package salao.online.application.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InformacaoDTO {
    
    private UUID idInformacao;
    private String nome;
    private String sobrenome;
    private int idade;
    private String email;
    private String telefone;
    private String senha;
}
