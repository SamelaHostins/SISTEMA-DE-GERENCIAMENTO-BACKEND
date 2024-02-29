package salao.online.application.services.interfaces;

import java.util.UUID;

import salao.online.application.dtos.ServicoDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ServicoService {

    public ServicoDTO inserirServico(ServicoDTO servicoDTO);

    public ServicoDTO atualizarServico(ServicoDTO servicoDTO) throws ValidacaoException;

    public ServicoDTO buscarServicoPorId(UUID idServico) throws ValidacaoException;
}

