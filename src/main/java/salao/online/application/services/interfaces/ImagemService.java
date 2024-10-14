package salao.online.application.services.interfaces;

import java.util.Optional;
import java.util.UUID;

import salao.online.application.dtos.ImagemDTO;

public interface ImagemService {

    public String uploadImagem(byte[] imageBytes, String nomeArquivo);

    public ImagemDTO salvarImagem(byte[] imageBytes, String nomeArquivo);

    public Optional<ImagemDTO> atualizarImagem(UUID id, byte[] imageBytes, String nomeArquivo);

    public Optional<ImagemDTO> buscarImagemPorId(UUID id);

    public boolean deletarImagem(UUID id);
}
