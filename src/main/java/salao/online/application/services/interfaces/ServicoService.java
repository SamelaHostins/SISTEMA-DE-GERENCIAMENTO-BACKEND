package salao.online.application.services.interfaces;

import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosDoServico.AtualizarServicoDTO;
import salao.online.application.dtos.dtosDoServico.CriarServicoDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaLocalDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaServicoDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ServicoService {

    CriarServicoDTO cadastrarServico(CriarServicoDTO servicoDTO);

    AtualizarServicoDTO atualizarServico(AtualizarServicoDTO servicoDTO) throws ValidacaoException;

    void deletarCadastroServico(UUID idServico) throws ValidacaoException;

    List<ServicoDTO> listarServicosDoProfissional(UUID idProfissional, int tipoServico)
            throws ValidacaoException;

    List<PesquisaServicoDTO> pesquisarTodosServicos();

    List<ServicoDTO> listarTodosOsServicosDoProfissional(UUID idProfissional) throws ValidacaoException;

    List<PesquisaLocalDTO> pesquisarTodasAsCidadesComServicos();
}
