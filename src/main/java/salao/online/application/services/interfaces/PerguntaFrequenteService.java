package salao.online.application.services.interfaces;

import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.PerguntaFrequenteDTO;

public interface PerguntaFrequenteService {

    PerguntaFrequenteDTO adicionarPergunta(UUID idProfissional, PerguntaFrequenteDTO dto);

    PerguntaFrequenteDTO atualizarPergunta(UUID idPergunta, UUID idProfissional, PerguntaFrequenteDTO dto);

    List<PerguntaFrequenteDTO> listarPerguntasPorProfissional(UUID idProfissional);

    void deletarPergunta(UUID idPergunta, UUID idProfissional);

    PerguntaFrequenteDTO buscarPorId(UUID id);
}
