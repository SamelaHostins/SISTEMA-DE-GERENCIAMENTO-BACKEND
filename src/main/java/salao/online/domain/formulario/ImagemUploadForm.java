package salao.online.domain.formulario;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import jakarta.ws.rs.FormParam;

/* Formulário de Upload:
Usada para capturar dados de uma requisição HTTP multipart/form-data.
Contém campos que representam os dados que o usuário está enviando 
(como arquivos e outros parâmetros).
Não é persistida no banco de dados; em vez disso, é convertida em uma 
entidade ou outro objeto de negócios quando os dados são processados.
 */
public class ImagemUploadForm {
    
    @FormParam("file")
    @PartType("application/octet-stream") // Especifica o tipo de mídia do arquivo
    private byte[] imageBytes;

    @FormParam("nomeArquivo") // Adicionando o parâmetro para o nome do arquivo
    private String nomeArquivo;

    // Getters e Setters
    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}