package salao.online.application.dtos.dtosDeCliente;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarSenhaClienteDTO {

    @NotBlank
    private String novaSenha;
}
