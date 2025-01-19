package salao.online.application.services.interfaces;

import java.io.InputStream;
import java.util.UUID;

import salao.online.domain.entities.Imagem;
import salao.online.domain.exceptions.ValidacaoException;

public interface ImagemService {

      public String uploadImagem(InputStream imageBytes, String nomeArquivo, int tipoImagem, UUID idUsuario,
                  boolean ehProfissional) throws ValidacaoException;

      public Imagem buscarImagemPorId(UUID id);
}
