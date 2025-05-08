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
public class BuscarProfissionalAutenticadoDTO {

    private UUID idProfissional;
    private String email;
}
