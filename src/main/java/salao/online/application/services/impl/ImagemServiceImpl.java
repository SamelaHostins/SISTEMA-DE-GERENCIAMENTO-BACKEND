package salao.online.application.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import salao.online.application.services.interfaces.ImagemService;
import salao.online.domain.entities.Cliente;
import salao.online.domain.entities.Imagem;
import salao.online.domain.entities.Profissional;
import salao.online.domain.enums.TipoImagemEnum;
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

    public ImagemServiceImpl() {
        // Configurar Cloudinary com suas credenciais
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dx2jocpeo",
                "api_key", "745685656596579",
                "api_secret", "ZRJ_kcRwt27XR7XPbWg9tgl1dAM"));
    }

    public String uploadImagem(InputStream imageBytes, String nomeArquivo) {
        byte[] bytes = null;
        try {
            bytes = imageBytes.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                imageBytes.close(); // Fechar o InputStream
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> options = new HashMap<>();
        options.put("public_id", nomeArquivo);

        logger.info("Fazendo o upload no Cloudinary");
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadedFile = cloudinary.uploader().upload(bytes, options);
            return (String) uploadedFile.get("secure_url");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Imagem salvarImagem(InputStream imageBytes, String nomeArquivo, TipoImagemEnum tipoImagem,
            UUID usuarioId, boolean isProfissional) {
        // Realiza o upload da imagem para o Cloudinary e obtém a URL da imagem
        String urlImagem = uploadImagem(imageBytes, nomeArquivo);
        if (urlImagem == null) {
            logger.error("Falha no upload da imagem.");
            throw new RuntimeException("Erro ao fazer upload da imagem.");
        }
        logger.info("Criando a imagem com a url: " + urlImagem);
        Imagem imagem = new Imagem();
        imagem.setUrlImagem(urlImagem);
        imagem.setNomeArquivo(nomeArquivo);
        imagem.setTipoImagem(tipoImagem);

        if (isProfissional) {
            Profissional profissional = profissionalRepository.findById(usuarioId);
            imagem.setProfissional(profissional);
        } else {
            Cliente cliente = clienteRepository.findById(usuarioId);
            imagem.setCliente(cliente);
        }

        logger.info("Salvando a imagem no banco de dados com a URL: " + urlImagem);
        imagemRepository.persistAndFlush(imagem);
        return imagem;
    }

    public Imagem buscarImagemPorId(UUID id) {
        return imagemRepository.findById(id);
    }

}