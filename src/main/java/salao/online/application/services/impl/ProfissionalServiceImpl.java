package salao.online.application.services.impl;

import java.text.Normalizer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.CriarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ProfissionalDTO;
import salao.online.application.mappers.EstoqueMapper;
import salao.online.application.mappers.ImagemMapper;
import salao.online.application.mappers.ProfissionalMapper;
import salao.online.application.mappers.ServicoMapper;
import salao.online.application.services.interfaces.ProfissionalService;
import salao.online.domain.entities.Profissional;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.ProfissionalRepository;

@ApplicationScoped
public class ProfissionalServiceImpl implements ProfissionalService {

    @Inject
    ProfissionalMapper profissionalMapper;

    @Inject
    ServicoMapper servicoMapper;

    @Inject
    EstoqueMapper estoqueMapper;

    @Inject
    ImagemMapper imagemMapper;

    @Inject
    ProfissionalRepository profissionalRepository;

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    private String removeAcentos(String texto) {
        if (texto == null) {
            return null;
        }
        String normalized = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return normalized.replaceAll("[^\\p{ASCII}]", "");
    }

    @Override
    @Transactional
    public CriarProfissionalDTO cadastrarProfissional(CriarProfissionalDTO profissionalDTO) {
        try {
            Profissional profissional = profissionalMapper.fromCriarDtoToEntity(profissionalDTO);
            String[] sobrenomes = profissionalDTO.getSobrenome().split(" ");
            String ultimoSobrenome = sobrenomes[sobrenomes.length - 1];

            // Remover acentos e definir o nome de usuário
            String usuario = removeAcentos(profissionalDTO.getNome().toLowerCase()) + "." +
                    removeAcentos(ultimoSobrenome.toLowerCase());
            profissional.setUsuario(usuario);

            logger.info("Salvando o profissional no banco de dados");
            profissionalRepository.persistAndFlush(profissional);

            return profissionalMapper.fromEntityToCriarDto(profissional);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar profissional.", e);
        }
    }

    @Override
    public ProfissionalDTO atualizarProfissional(ProfissionalDTO profissionalDTO) throws ValidacaoException {
        throw new UnsupportedOperationException("Unimplemented method 'atualizarProfissional'");
    }

    @Override
    public BuscarProfissionalDTO buscarProfissionalPorId(UUID idProfissional) throws ValidacaoException {
        logger.info("Validando se o Profissional existe");
        Profissional profissional = profissionalRepository.findByIdOptional(idProfissional)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));
        return getBuscarProfissionalDTO(profissional);
    }

    @Override
    public ListarProfissionalDTO listarProfissionalPorId(UUID idProfissional) throws ValidacaoException {
        logger.info("Validando se o Profissional existe");
        Profissional profissional = profissionalRepository.findByIdOptional(idProfissional)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));
        return getListarProfissionalDTO(profissional);
    }

    @Override
    @Transactional
    public List<ListarProfissionalDTO> listarTodosProfissionais() {
        List<Profissional> profissionais = profissionalRepository.findAll().list();
        return profissionais.stream()
                .map(profissional -> profissionalMapper.fromEntityToListarDto(profissional))
                .collect(Collectors.toList());
    }

    @Override
    public void deletarProfissional(UUID idProfissional) throws ValidacaoException {
        logger.info("Validando se o Cliente existe");
        buscarProfissionalPorId(idProfissional);
        profissionalRepository.deletarProfissional(idProfissional);
    }

    private BuscarProfissionalDTO getBuscarProfissionalDTO(Profissional profissional) {
        BuscarProfissionalDTO profissionalDTO = profissionalMapper.fromEntityToBuscarDto(profissional);
        profissionalDTO.setServicos(servicoMapper.fromEntityListToDtoList(profissional.getServicos()));
        profissionalDTO.setEstoques(estoqueMapper.fromEntityListToDtoList(profissional.getEstoques()));
        profissionalDTO.setImagens(imagemMapper.fromEntityListToProfissionalDtoList(profissional.getImagens()));
        return profissionalDTO;
    }

    private ListarProfissionalDTO getListarProfissionalDTO(Profissional profissional) {
        ListarProfissionalDTO profissionalDTO = profissionalMapper.fromEntityToListarDto(profissional);
        profissionalDTO.setServicos(servicoMapper.fromEntityListToDtoList(profissional.getServicos()));
        return profissionalDTO;
    }

}
