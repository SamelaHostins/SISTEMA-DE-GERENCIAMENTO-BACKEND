package salao.online.application.services.impl;

import java.text.Normalizer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import salao.online.application.dtos.dtosDeEndereco.AtualizarEnderecoDTO;
import salao.online.application.dtos.dtosDeEndereco.BuscarEnderecoDoProfissionalDTO;
import salao.online.application.dtos.dtosDeImagem.ImagemDTO;
import salao.online.application.dtos.dtosDoProfissional.AtualizarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalAutenticadoDTO;
import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.CriarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalEmDestaqueDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaProfissionalDTO;
import salao.online.application.mappers.EstoqueMapper;
import salao.online.application.mappers.ImagemMapper;
import salao.online.application.mappers.ProfissionalMapper;
import salao.online.application.mappers.ServicoMapper;
import salao.online.application.services.interfaces.ImagemService;
import salao.online.application.services.interfaces.ProfissionalService;
import salao.online.domain.entities.Profissional;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.enums.ProfissaoEsteticaEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.EnderecoRepository;
import salao.online.infra.repositories.HorarioTrabalhoRepository;
import salao.online.infra.repositories.ProfissionalRepository;

@ApplicationScoped
public class ProfissionalServiceImpl implements ProfissionalService {

    @Inject
    ProfissionalRepository profissionalRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    ProfissionalMapper profissionalMapper;

    @Inject
    ServicoMapper servicoMapper;

    @Inject
    EstoqueMapper estoqueMapper;

    @Inject
    ImagemMapper imagemMapper;

    @Inject
    ImagemService imagemService;

    @Inject
    HorarioTrabalhoRepository horarioTrabalhoRepository;

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
    public CriarProfissionalDTO cadastrarProfissional(CriarProfissionalDTO profissionalDTO) throws ValidacaoException {
        try {
            if (profissionalRepository.find("email", profissionalDTO.getEmail()).firstResultOptional().isPresent()) {
                throw new ValidacaoException(MensagemErroValidacaoEnum.EMAIL_JA_CADASTRADO.getMensagemErro() + " "
                        + profissionalDTO.getEmail());
            }

            if (profissionalRepository.find("documento", profissionalDTO.getDocumento()).firstResultOptional()
                    .isPresent()) {
                throw new ValidacaoException(MensagemErroValidacaoEnum.DOCUMENTO_JA_CADASTRADO.getMensagemErro() + " "
                        + profissionalDTO.getDocumento());
            }

            Profissional profissional = profissionalMapper.fromCriarDtoToEntity(profissionalDTO);

            // Gerar nome de usuário automaticamente com nome.sobrenome
            String[] sobrenomes = profissionalDTO.getSobrenome().split(" ");
            String ultimoSobrenome = sobrenomes[sobrenomes.length - 1];
            String usuario = removeAcentos(profissionalDTO.getNome().toLowerCase()) + "." +
                    removeAcentos(ultimoSobrenome.toLowerCase());
            profissional.setUsuario(usuario);

            logger.info("Salvando o profissional no banco de dados");
            profissionalRepository.persistAndFlush(profissional);

            return profissionalDTO; // ou outro DTO se preferir

        } catch (ValidacaoException e) {
            logger.warn("Erro de validação ao cadastrar profissional: " + e.getMessage());
            throw e;

        } catch (Exception e) {
            logger.error("Erro ao cadastrar profissional", e);
            throw new RuntimeException("Erro ao cadastrar profissional.", e);
        }
    }

    @Override
    @Transactional
    public AtualizarProfissionalDTO atualizarProfissional(AtualizarProfissionalDTO dto) throws ValidacaoException {
        Profissional profissional = profissionalRepository.findByIdOptional(dto.getIdProfissional())
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));
        // Converte o int para enum
        ProfissaoEsteticaEnum profissaoEnum = ProfissaoEsteticaEnum.fromProfissao(dto.getProfissao());

        profissional.atualizarProfissional(
                dto.getInstagram(),
                profissaoEnum,
                dto.getDescricaoDaProfissao(),
                dto.getNome(),
                dto.getUsuario(),
                dto.getSobrenome(),
                dto.getEmail(),
                dto.getTelefone());

        logger.info("Salvando profissional atualizado");
        profissionalRepository.persistAndFlush(profissional);

        return profissionalMapper.fromEntityToAtualizarDto(profissional);
    }

    @Override
    @Transactional
    public void atualizarEndereco(UUID idProfissional, AtualizarEnderecoDTO dto) throws ValidacaoException {
        logger.info("Validando se o Profissional existe para atualizar endereco");
        Profissional profissional = profissionalRepository.findByIdOptional(idProfissional)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));

        profissional.atualizarEndereco(
                dto.getRua(),
                dto.getNumero(),
                dto.getBairro(),
                dto.getCidade(),
                dto.getEstado(),
                dto.getCep(),
                dto.getNome(),
                dto.getComplemento());

        logger.info("Atualizando endereço no banco de dados");
        profissionalRepository.persistAndFlush(profissional);
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
    public List<ListarProfissionalDTO> listarTodosProfissionais() {
        List<Profissional> profissionais = profissionalRepository.findAll().list();
        return profissionais.stream()
                .map(profissional -> profissionalMapper.fromEntityToListarDto(profissional))
                .collect(Collectors.toList());
    }

    @Override
    public List<PesquisaProfissionalDTO> pesquisarTodosProfissionais() {
        // 1) busca só profissionais com imagem de perfil
        List<Profissional> profissionais = profissionalRepository.pesquisarTodosComImagemDePerfil();
        return profissionais.stream()
                .map(profissionalMapper::fromEntityToPesquisaDto)
                .collect(Collectors.toList());
    }

    @Override
    public BuscarEnderecoDoProfissionalDTO BuscarEnderecoDoProfissionalDTO(UUID idProfissional)
            throws ValidacaoException {
        Profissional profissional = profissionalRepository.findByIdOptional(idProfissional)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));
        return profissionalMapper.fromEntityToBuscarEnderecoDto(profissional);
    }

    @Override
    public void deletarProfissional(UUID idProfissional) throws ValidacaoException {
        logger.info("Validando se o Cliente existe");
        buscarProfissionalPorId(idProfissional);
        profissionalRepository.deletarProfissional(idProfissional);
    }

    @Override
    public BuscarProfissionalAutenticadoDTO buscarProfissionalAutenticado(UUID idProfissional)
            throws ValidacaoException {
        Profissional entity = profissionalRepository
                .findByIdOptional(idProfissional)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));
        return profissionalMapper.toAutenticadoDto(entity);
    }

    @Override
    public List<ListarProfissionalEmDestaqueDTO> listarProfissionaisEmDestaque() {
        // 1) Já retorna só os 9 primeiros com imagem de perfil
        List<Profissional> profissionais = profissionalRepository.listarComImagemDePerfil(9);

        // 2) Mapeia para DTO, priorizando portfólio → perfil
        return profissionais.stream()
                .map(p -> {
                    UUID id = p.getIdProfissional();

                    // usará a ft do portfolio
                    List<ImagemDTO> imagensPortfolio = imagemService.buscarFotosDoPortfolio(id);
                    String urlImagem;
                    if (imagensPortfolio != null && !imagensPortfolio.isEmpty()) {
                        urlImagem = imagensPortfolio.get(0).getUrlImagem();
                    } else {
                        // se ele não tem, usará a de perfil
                        ImagemDTO imgPerfil = imagemService.buscarImagemDePerfil(id);
                        urlImagem = imgPerfil.getUrlImagem();
                    }

                    // monta DTO via mapper e injeta a URL
                    ListarProfissionalEmDestaqueDTO dto = profissionalMapper.fromEntityToDestaqueDto(p);
                    dto.setUrlImagem(urlImagem);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void alterarSenha(UUID idProfissional, String novaSenha) throws ValidacaoException {
        Profissional profissional = profissionalRepository.findById(idProfissional);
        if (profissional == null) {
            throw new ValidacaoException(
                    MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro());
        }

        profissional.setSenha(novaSenha);
        profissionalRepository.persistAndFlush(profissional); 
    }

    private BuscarProfissionalDTO getBuscarProfissionalDTO(Profissional profissional) {
        BuscarProfissionalDTO profissionalDTO = profissionalMapper.fromEntityToBuscarDto(profissional);
        profissionalDTO.setServicos(servicoMapper.fromEntityListToDtoList(profissional.getServicos()));
        return profissionalDTO;
    }

    private ListarProfissionalDTO getListarProfissionalDTO(Profissional profissional) {
        ListarProfissionalDTO profissionalDTO = profissionalMapper.fromEntityToListarDto(profissional);
        return profissionalDTO;
    }

}
