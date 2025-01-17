package salao.online.application.dtos.dtosDeImagem;

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
    
    private String urlImagem;
    private TipoImagemEnum tipoImagem;
}
