package salao.online.application.dtos.dtosDoProfissional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriarProfissionalDTO{

    private String nome;
    private String sobrenome;
    private short idade;
    private String email;
    private String telefone;
    private String rua;
    private String bairro;
    private String cidade;
    private int numero;
    private String cep;
    private String senha;
}
