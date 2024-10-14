package salao.online.domain.configuracao;

import java.io.InputStream;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class ImagemUploadForm {

    @PartType("application/octet-stream")
    private InputStream imageBytes;

    @PartType("text/plain")
    private String nomeArquivo;

    public InputStream getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(InputStream imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}
