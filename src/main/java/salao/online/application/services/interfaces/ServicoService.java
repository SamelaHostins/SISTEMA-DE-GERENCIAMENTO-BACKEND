package salao.online.application.services.interfaces;

import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosDoServico.AtualizarServicoDTO;
import salao.online.application.dtos.dtosDoServico.CriarServicoDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaServicoDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ServicoService {

    public CriarServicoDTO cadastrarServico(CriarServicoDTO servicoDTO);

    public AtualizarServicoDTO atualizarServico(AtualizarServicoDTO servicoDTO) throws ValidacaoException;

    public void deletarCadastroServico(UUID idServico) throws ValidacaoException;

    public List<ServicoDTO> listarServicosDoProfissional(UUID idProfissional, int tipoServico)
            throws ValidacaoException;

    public List<PesquisaServicoDTO> pesquisarTodosServicos();

    public List<ServicoDTO> listarTodosOsServicosDoProfissional(UUID idProfissional) throws ValidacaoException;

}
