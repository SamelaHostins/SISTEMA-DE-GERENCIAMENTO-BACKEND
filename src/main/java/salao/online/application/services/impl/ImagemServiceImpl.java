package salao.online.application.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import salao.online.application.dtos.dtosDeImagem.ImagemDTO;
import salao.online.application.dtos.dtosDeImagem.TipoImagemEnumDTO;
import salao.online.application.mappers.ImagemMapper;
import salao.online.application.services.interfaces.ImagemService;
import salao.online.domain.entities.Cliente;
import salao.online.domain.entities.Imagem;
import salao.online.domain.entities.Profissional;
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

    // public String uploadImagem(InputStream imageBytes, String nomeArquivo,
    // TipoImagemEnum tipoImagem,
    // UUID idUsuario, boolean ehProfissional) {
    // Path tempFilePath = null;
    // try {
    // // Cria um arquivo temporário para leitura dos bytes
    // tempFilePath = Files.createTempFile("upload_", nomeArquivo);
    // Files.copy(imageBytes, tempFilePath, StandardCopyOption.REPLACE_EXISTING);

    // // Le os bytes do arquivo temporário
    // byte[] bytes = Files.readAllBytes(tempFilePath);
    // Map<String, Object> options = new HashMap<>();
    // options.put("public_id", nomeArquivo);

    // logger.info("Fazendo o upload no Cloudinary");
    // @SuppressWarnings("unchecked")
    // Map<String, Object> uploadedFile = cloudinary.uploader().upload(bytes,
    // options);
    // // Recupera a URL segura gerada pelo Cloudinary
    // String secureUrl = (String) uploadedFile.get("secure_url");

    // // Salvar as informações no banco de dados
    // salvarImagem(secureUrl, nomeArquivo, tipoImagem, idUsuario, ehProfissional);
    // return secureUrl;

    // } catch (Exception e) {
    // logger.error("Erro ao fazer upload no Cloudinary", e);
    // throw new RuntimeException("Erro ao fazer upload da imagem.");
    // } finally {
    // // Fechar o InputStream e excluir o arquivo temporário
    // try {
    // imageBytes.close();
    // logger.info("InputStream fechado com sucesso.");
    // } catch (IOException e) {
    // logger.error("Erro ao fechar InputStream", e);
    // }
    // }
    // }

    // public void salvarImagem(String urlImagem, String nomeArquivo, TipoImagemEnum
    // tipoImagem, UUID idUsuario,
    // boolean ehProfissional) {
    // logger.info("Salvando informações do upload no banco de dados...");

    // Imagem imagem = new Imagem();
    // imagem.setUrlImagem(urlImagem);
    // imagem.setNomeArquivo(nomeArquivo);
    // imagem.setTipoImagem(tipoImagem);

    // if (ehProfissional) {
    // Profissional profissional = profissionalRepository.findById(idUsuario);
    // if (profissional == null) {
    // throw new IllegalArgumentException("Profissional não encontrado.");
    // }
    // imagem.setProfissional(profissional);
    // } else {
    // Cliente cliente = clienteRepository.findById(idUsuario);
    // if (cliente == null) {
    // throw new IllegalArgumentException("Cliente não encontrado.");
    // }
    // imagem.setCliente(cliente);
    // }

    // imagemRepository.persistAndFlush(imagem);
    // logger.info("Informações do upload salvas com sucesso no banco de dados.");
    // }

    public String uploadImagem(InputStream imageBytes, String nomeArquivo, int tipoImagem, UUID idUsuario,
            boolean ehProfissional) {
        TipoImagemEnumDTO tipoImagemEnum = TipoImagemEnumDTO.fromTipoImagem(tipoImagem);

        // Lógica de upload permanece a mesma
        Path tempFilePath = null;
        try {
            tempFilePath = Files.createTempFile("upload_", nomeArquivo);
            Files.copy(imageBytes, tempFilePath, StandardCopyOption.REPLACE_EXISTING);

            byte[] bytes = Files.readAllBytes(tempFilePath);
            Map<String, Object> options = new HashMap<>();
            options.put("public_id", nomeArquivo);

            logger.info("Fazendo o upload no Cloudinary");
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadedFile = cloudinary.uploader().upload(bytes, options);
            String secureUrl = (String) uploadedFile.get("secure_url");

            // Cria o DTO
            ImagemDTO imagemDTO = new ImagemDTO();
            imagemDTO.setUrlImagem(secureUrl);
            imagemDTO.setNomeArquivo(nomeArquivo);
            imagemDTO.setTipoImagem(tipoImagemEnum);
            imagemDTO.setIdUsuario(idUsuario);
            imagemDTO.setEhProfissional(ehProfissional);

            // Salva a imagem
            salvarImagem(imagemDTO);

            return secureUrl;

        } catch (Exception e) {
            logger.error("Erro ao fazer upload no Cloudinary", e);
            throw new RuntimeException("Erro ao fazer upload da imagem.");
        } finally {
            try {
                imageBytes.close();
                logger.info("InputStream fechado com sucesso.");
            } catch (IOException e) {
                logger.error("Erro ao fechar InputStream", e);
            }
        }
    }

    public void salvarImagem(ImagemDTO imagemDTO) {
        logger.info("Salvando informações do upload no banco de dados...");

        // Usa o mapper para criar a entidade
        Imagem imagem = imagemMapper.fromDtoToEntity(imagemDTO);

        // Associa o profissional ou cliente, se necessário
        if (imagemDTO.isEhProfissional()) {
            Profissional profissional = profissionalRepository.findById(imagemDTO.getIdUsuario());
            if (profissional == null) {
                throw new IllegalArgumentException("Profissional não encontrado.");
            }
            imagem.setProfissional(profissional);
        } else {
            Cliente cliente = clienteRepository.findById(imagemDTO.getIdUsuario());
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente não encontrado.");
            }
            imagem.setCliente(cliente);
        }

        // Persiste no banco
        imagemRepository.persistAndFlush(imagem);
        logger.info("Informações do upload salvas com sucesso no banco de dados.");
    }

    public Imagem buscarImagemPorId(UUID id) {
        return imagemRepository.findById(id);
    }

}