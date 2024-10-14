package salao.online.application.services.interfaces;

import java.io.InputStream;

//para adicionar e retornar os dados da imagem
public interface ImagemService {

     public String uploadImagem(InputStream imageBytes, String nomeArquivo);
}
