package salao.online.application.services.interfaces;

import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosDoProfissional.AtualizarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.CriarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaProfissionalDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ProfissionalService {

    public CriarProfissionalDTO cadastrarProfissional(CriarProfissionalDTO profissionalDTO);

    public AtualizarProfissionalDTO atualizarProfissional(AtualizarProfissionalDTO profissionalDTO) throws ValidacaoException;

    public BuscarProfissionalDTO buscarProfissionalPorId(UUID idProfissional) throws ValidacaoException;

    public ListarProfissionalDTO listarProfissionalPorId(UUID idProfissional) throws ValidacaoException;

    public List<ListarProfissionalDTO> listarTodosProfissionais();

    public void deletarProfissional(UUID idProfissional) throws ValidacaoException;

    public List<PesquisaProfissionalDTO> pesquisarTodosProfissionais();
}
