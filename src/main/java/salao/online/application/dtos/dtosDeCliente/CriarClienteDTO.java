package salao.online.application.dtos.dtosDeCliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriarClienteDTO {

    private String nome;
    private String sobrenome;
    private short idade;
    private String email;
    private String telefone;
    private String senha;
}
