package salao.online.application.services.interfaces;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosDeImagem.ImagemDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ImagemService {

      String uploadImagem(InputStream imageBytes, int tipoImagem, UUID idUsuario,
                  boolean ehProfissional) throws ValidacaoException;

      void excluirImagem(UUID idImagem);

      ImagemDTO buscarImagemDePerfil(UUID idUsuario);

      List<ImagemDTO> buscarFotosDoPortfolio(UUID idUsuario);
}
