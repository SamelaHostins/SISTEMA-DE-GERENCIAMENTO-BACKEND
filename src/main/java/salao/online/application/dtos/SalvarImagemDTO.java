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
public class SalvarImagemDTO {

    private String urlImagem;
    private String nomeArquivo;
    private TipoImagemEnumDTO tipoImagem;
    private UUID usuarioId;
    private boolean isProfissional;

}
