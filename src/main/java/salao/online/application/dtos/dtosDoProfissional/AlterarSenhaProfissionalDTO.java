package salao.online.application.dtos.dtosDoProfissional;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarSenhaProfissionalDTO {

    @NotBlank
    private String novaSenha;
}
