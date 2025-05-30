package salao.online.application.dtos.dtosDeCliente;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AtualizarClienteDTO {

    private UUID idCliente;
    private String nome;
    private String usuario;
    private String sobrenome;
    private String email;
    private String telefone;
}
