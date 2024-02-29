package salao.online.application.services.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.AvaliacaoDTO;
import salao.online.domain.exceptions.ValidacaoException;


public interface AvaliacaoService {

    public AvaliacaoDTO inserirAvaliacao(AvaliacaoDTO avaliacaoDTO) throws ValidacaoException;

    public AvaliacaoDTO atualizarAvaliacao(AvaliacaoDTO avaliacaoDTO) throws ValidacaoException;

    public BigDecimal buscarMediaAvaliacoesServico(UUID idServico) throws ValidacaoException;

    public List<AvaliacaoDTO> buscarAvaliacoesDoServico(UUID idServico);

    public List<AvaliacaoDTO> buscarAvaliacoesDoCliente(UUID idCliente) throws ValidacaoException;

    public AvaliacaoDTO buscarAvaliacaoClienteServico(UUID idCliente, UUID idServico)
            throws ValidacaoException;
}
