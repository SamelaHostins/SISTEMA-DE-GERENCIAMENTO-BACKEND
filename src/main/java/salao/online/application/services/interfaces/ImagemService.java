package salao.online.application.services.interfaces;

import java.io.InputStream;
import java.util.UUID;

import salao.online.application.dtos.SalvarImagemDTO;
import salao.online.domain.entities.Imagem;
import salao.online.domain.enums.TipoImagemEnum;

//para adicionar e retornar os dados da imagem
public interface ImagemService {

      // public String uploadImagem(InputStream imageBytes, String nomeArquivo);

      public String uploadImagem(InputStream imageBytes, String nomeArquivo, TipoImagemEnum tipoImagem,
                  UUID idUsuario, boolean ehProfissional);

      // public SalvarImagemDTO salvarImagem(SalvarImagemDTO dto);

      public Imagem buscarImagemPorId(UUID id);
}
