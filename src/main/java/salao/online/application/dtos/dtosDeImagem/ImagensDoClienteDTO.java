package salao.online.application.dtos.dtosDeImagem;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.domain.enums.TipoImagemEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImagensDoClienteDTO {
    
    private UUID idImagem;
    private String urlImagem;
    private TipoImagemEnum tipoImagem;
}
