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
import salao.online.application.dtos.SalvarImagemDTO;
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

    public String uploadImagem(InputStream imageBytes, String nomeArquivo) {
        Path tempFilePath = null;
        try {
            // Crie um arquivo temporário para leitura dos bytes, se necessário
            tempFilePath = Files.createTempFile("upload_", nomeArquivo);
            Files.copy(imageBytes, tempFilePath, StandardCopyOption.REPLACE_EXISTING);

            // Leia os bytes do arquivo temporário
            byte[] bytes = Files.readAllBytes(tempFilePath);
            Map<String, Object> options = new HashMap<>();
            options.put("public_id", nomeArquivo);

            logger.info("Fazendo o upload no Cloudinary");
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadedFile = cloudinary.uploader().upload(bytes, options);
            return (String) uploadedFile.get("secure_url");
        } catch (Exception e) {
            logger.error("Erro ao fazer upload no Cloudinary", e);
            throw new RuntimeException("Erro ao fazer upload da imagem.");
        } finally {
            // Fechar o InputStream e excluir o arquivo temporário
            try {
                imageBytes.close();
                logger.info("InputStream fechado com sucesso.");
            } catch (IOException e) {
                logger.error("Erro ao fechar InputStream", e);
            }

            // Excluir o arquivo temporário, se criado
            if (tempFilePath != null) {
                try {
                    Files.deleteIfExists(tempFilePath);
                    logger.info("Arquivo temporário excluído com sucesso.");
                } catch (IOException e) {
                    logger.error("Erro ao excluir o arquivo temporário: ", e);
                }
            }
        }
    }

    @Override
    public SalvarImagemDTO salvarImagem(SalvarImagemDTO dto) {
        Imagem imagem = imagemMapper.toEntity(dto);

        // Verificar e associar corretamente
        if (dto.ehProfissional()) {
            Profissional profissional = profissionalRepository.findById(dto.getIdUsuario());
            if (profissional == null) {
                throw new IllegalArgumentException("Profissional não encontrado.");
            }
            imagem.setProfissional(profissional);
        } else {
            Cliente cliente = clienteRepository.findById(dto.getIdUsuario());
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente não encontrado.");
            }
            imagem.setCliente(cliente);
        }

        // Salvar a imagem no banco
        imagemRepository.persistAndFlush(imagem);

        return imagemMapper.toDto(imagem);
    }

    public Imagem buscarImagemPorId(UUID id) {
        return imagemRepository.findById(id);
    }

}