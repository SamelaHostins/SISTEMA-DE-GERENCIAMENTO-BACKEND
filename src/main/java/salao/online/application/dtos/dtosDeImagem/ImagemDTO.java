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
public class ImagemDTO {

    private String urlImagem;
    private String nomeArquivo;
    private TipoImagemEnumDTO tipoImagem;
    private UUID idUsuario;
    private boolean ehProfissional;

    public boolean ehProfissional() {
        return ehProfissional;
    }

    public void setEhProfissional(boolean ehProfissional) {
        this.ehProfissional = ehProfissional;
    }

}
