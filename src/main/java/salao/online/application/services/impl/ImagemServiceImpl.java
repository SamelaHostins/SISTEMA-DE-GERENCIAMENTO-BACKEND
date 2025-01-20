package salao.online.application.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import salao.online.application.dtos.dtosDeImagem.ImagemDTO;
import salao.online.application.dtos.dtosDeImagem.TipoImagemEnumDTO;
import salao.online.application.mappers.ImagemMapper;
import salao.online.application.services.interfaces.ImagemService;
import salao.online.domain.entities.Cliente;
import salao.online.domain.entities.Imagem;
import salao.online.domain.entities.Profissional;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.enums.TipoImagemEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.ClienteRepository;
import salao.online.infra.repositories.ImagemRepository;
import salao.online.infra.repositories.ProfissionalRepository;

@ApplicationScoped
public class ImagemServiceImpl implements ImagemService {

    private Cloudinary cloudinary;
    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Inject
    ImagemRepository imagemRepository;

    @Inject
    ProfissionalRepository profissionalRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ImagemMapper imagemMapper;

    public ImagemServiceImpl() {
        // Configurar Cloudinary com suas credenciais
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dx2jocpeo",
                "api_key", "745685656596579",
                "api_secret", "ZRJ_kcRwt27XR7XPbWg9tgl1dAM"));
    }

    private byte[] readBytesFromInputStream(InputStream inputStream, String fileName) throws IOException {
        Path tempFilePath = Files.createTempFile("upload_", fileName);
        Files.copy(inputStream, tempFilePath, StandardCopyOption.REPLACE_EXISTING);
        byte[] bytes = Files.readAllBytes(tempFilePath);
        Files.deleteIfExists(tempFilePath);
        return bytes;
    }

    @Transactional
    public String uploadImagem(InputStream imageBytes, int tipoImagem, UUID idUsuario,
            boolean ehProfissional) throws ValidacaoException {
        TipoImagemEnumDTO tipoImagemEnum = TipoImagemEnumDTO.fromTipoImagem(tipoImagem);

        try {
            String nomeArquivo = gerarNomeArquivoAutomatico(idUsuario);
            byte[] bytes = readBytesFromInputStream(imageBytes, nomeArquivo);

            Map<String, Object> options = new HashMap<>();
            options.put("public_id", nomeArquivo);

            @SuppressWarnings("unchecked")
            Map<String, Object> uploadedFile = cloudinary.uploader().upload(bytes, options);
            String secureUrl = (String) uploadedFile.get("secure_url");

            ImagemDTO imagemDTO = new ImagemDTO();
            imagemDTO.setUrlImagem(secureUrl);
            imagemDTO.setNomeArquivo(nomeArquivo);
            imagemDTO.setTipoImagem(tipoImagemEnum);
            imagemDTO.setIdUsuario(idUsuario);
            imagemDTO.setEhProfissional(ehProfissional);

            salvarImagem(imagemDTO);

            return secureUrl;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload da imagem.", e);
        } finally {
            try {
                if (imageBytes != null) {
                    imageBytes.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Erro ao fechar InputStream.", e);
            }
        }
    }

    public void salvarImagem(ImagemDTO imagemDTO) throws ValidacaoException {
        logger.info("Salvando informações do upload no banco de dados...");
        Imagem imagem = imagemMapper.fromDtoToEntity(imagemDTO);

        if (imagemDTO.isEhProfissional()) {
            Profissional profissional = profissionalRepository.findById(imagemDTO.getIdUsuario());
            if (profissional == null) {
                throw new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro());
            }
            imagem.setProfissional(profissional);
        } else {
            Cliente cliente = clienteRepository.findById(imagemDTO.getIdUsuario());
            if (cliente == null) {
                throw new ValidacaoException(
                        MensagemErroValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getMensagemErro());
            }
            imagem.setCliente(cliente);
        }

        imagemRepository.persistAndFlush(imagem);
        logger.info("Informações do upload salvas com sucesso no banco de dados.");
    }

    public void atualizarImagem(UUID idImagem, InputStream novaImagemBytes, String novoNomeArquivo) {
        Imagem imagemExistente = imagemRepository.findById(idImagem);
        if (imagemExistente == null) {
            throw new IllegalArgumentException("Imagem não encontrada.");
        }

        try {
            cloudinary.uploader().destroy(imagemExistente.getNomeArquivo(), new HashMap<>());

            byte[] bytes = readBytesFromInputStream(novaImagemBytes, novoNomeArquivo);

            Map<String, Object> options = new HashMap<>();
            options.put("public_id", novoNomeArquivo);

            @SuppressWarnings("unchecked")
            Map<String, Object> uploadedFile = cloudinary.uploader().upload(bytes, options);
            String newSecureUrl = (String) uploadedFile.get("secure_url");

            imagemExistente.setUrlImagem(newSecureUrl);
            imagemExistente.setNomeArquivo(novoNomeArquivo);
            imagemRepository.persist(imagemExistente);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar imagem.", e);
        }
    }

    @Transactional
    public void excluirImagem(UUID idImagem) {
        Imagem imagem = imagemRepository.findById(idImagem);
        if (imagem == null) {
            throw new IllegalArgumentException("Imagem não encontrada.");
        }

        try {
            logger.info("Excluindo imagem do Cloudinary: {}" + imagem.getNomeArquivo());
            cloudinary.uploader().destroy(imagem.getNomeArquivo(), new HashMap<>());

            logger.info("Excluindo imagem do banco de dados");
            imagemRepository.delete(imagem);

        } catch (Exception e) {
            logger.error("Erro ao excluir imagem.", e);
            throw new RuntimeException("Erro ao excluir imagem.", e);
        }
    }

    public ImagemDTO buscarImagemDePerfil(UUID idUsuario) {
        Imagem imagem = imagemRepository.buscarFoto(idUsuario, TipoImagemEnum.PERFIL);
        return imagem != null ? imagemMapper.fromEntityToDto(imagem) : null;
    }

    public List<ImagemDTO> buscarFotosDoPortfolio(UUID idUsuario) {
        List<Imagem> imagens = imagemRepository.buscarFotos(idUsuario, TipoImagemEnum.PORT);
        return imagens.stream()
                .map(imagemMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    private String gerarNomeArquivoAutomatico(UUID idUsuario) {
        String shortUuid = idUsuario.toString().substring(0, 8); // Primeiro segmento do UUID
        long timestamp = System.currentTimeMillis() / 1000; // Segundos desde Unix Epoch
        return shortUuid + "_" + timestamp;
    }

}