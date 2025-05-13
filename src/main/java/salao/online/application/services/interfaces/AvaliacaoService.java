package salao.online.application.services.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.AvaliacaoDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface AvaliacaoService {

    AvaliacaoDTO inserirAvaliacao(AvaliacaoDTO avaliacaoDTO) throws ValidacaoException;

    AvaliacaoDTO atualizarAvaliacao(AvaliacaoDTO avaliacaoDTO) throws ValidacaoException;

    BigDecimal buscarMediaAvaliacoesServico(UUID idServico) throws ValidacaoException;

    List<AvaliacaoDTO> buscarAvaliacoesDoServico(UUID idServico);

    List<AvaliacaoDTO> buscarAvaliacoesDoCliente(UUID idCliente) throws ValidacaoException;

    AvaliacaoDTO buscarAvaliacaoClienteServico(UUID idCliente, UUID idServico)
            throws ValidacaoException;
}
