package salao.online.application.dtos.dtosDeImagem;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImagensDoProfissionalDTO {

    private UUID idImagem;
    private String urlImagem;
    private UUID idProfissional;
}
