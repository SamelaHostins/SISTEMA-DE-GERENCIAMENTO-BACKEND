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
public class ImagemDTO {

    private UUID idImagem;
    private String urlImagem;
    private String nomeArquivo;
    private UUID idProfissional;
    private UUID idCliente;
}
