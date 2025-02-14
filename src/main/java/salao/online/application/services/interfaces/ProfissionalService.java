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

    CriarProfissionalDTO cadastrarProfissional(CriarProfissionalDTO profissionalDTO) throws ValidacaoException;

    AtualizarProfissionalDTO atualizarProfissional(AtualizarProfissionalDTO profissionalDTO) throws ValidacaoException;

    BuscarProfissionalDTO buscarProfissionalPorId(UUID idProfissional) throws ValidacaoException;

    ListarProfissionalDTO listarProfissionalPorId(UUID idProfissional) throws ValidacaoException;

    List<ListarProfissionalDTO> listarTodosProfissionais();

    List<PesquisaProfissionalDTO> pesquisarTodosProfissionais();

    void deletarProfissional(UUID idProfissional) throws ValidacaoException;
}
