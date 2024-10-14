package salao.online.application.services.impl;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import salao.online.application.dtos.ImagemDTO;
import salao.online.application.services.interfaces.ImagemService;
import salao.online.domain.entities.Imagem;
import salao.online.infra.repositories.ImagemRepository;

@ApplicationScoped
public class ImagemServiceImpl implements ImagemService {

    @Inject
    Cloudinary cloudinary; 

    @Inject
    ImagemRepository imagemRepository;

    @Inject // Faz com que reconheça o .env
    public ImagemServiceImpl(@ConfigProperty(name = "CLOUDINARY_URL") String cloudinaryUrl) {
        this.cloudinary = new Cloudinary(cloudinaryUrl);
    }

    // Método para fazer o upload da imagem e retornar a URL
    public String uploadImagem(byte[] imageBytes, String nomeArquivo) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = cloudinary.uploader().upload(imageBytes,
                    ObjectUtils.asMap("public_id", nomeArquivo));
            return uploadResult.get("secure_url").toString(); // Retorna a URL da imagem
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }

    // Método para salvar a imagem
    public ImagemDTO salvarImagem(byte[] imageBytes, String nomeArquivo) {
        String urlImagem = uploadImagem(imageBytes, "imagem_" + System.currentTimeMillis() + "_" + nomeArquivo);
        if (urlImagem != null) {
            Imagem imagem = new Imagem(urlImagem, nomeArquivo);
            imagemRepository.persist(imagem); 
            return new ImagemDTO(imagem.getIdImagem(), urlImagem, nomeArquivo); 
        }
        return null; 
    }

    public Optional<ImagemDTO> atualizarImagem(UUID id, byte[] imageBytes, String nomeArquivo) {
        Imagem imagemExistente = imagemRepository.findById(id);
        if (imagemExistente != null) {
            String urlImagem = uploadImagem(imageBytes, "imagem_" + System.currentTimeMillis() + "_" + nomeArquivo);
            if (urlImagem != null) {
                imagemExistente.setUrlImagem(urlImagem); // Atualiza a URL
                imagemExistente.setNomeArquivo(nomeArquivo); // Atualiza o nome do arquivo
                imagemRepository.persist(imagemExistente); 
                return Optional.of(new ImagemDTO(imagemExistente.getIdImagem(), urlImagem, nomeArquivo)); // Retorna o DTO atualizado
            }
        }
        return Optional.empty(); 
    }

    public Optional<ImagemDTO> buscarImagemPorId(UUID id) {
        Imagem imagem = imagemRepository.findById(id);
        if (imagem != null) {
            return Optional.of(new ImagemDTO(imagem.getIdImagem(), imagem.getUrlImagem(), imagem.getNomeArquivo())); // Retorna o DTO
        }
        return Optional.empty(); 
    }

    public boolean deletarImagem(UUID id) {
        Imagem imagem = imagemRepository.findById(id);
        if (imagem != null) {
            imagemRepository.delete(imagem); 
            return true; // Retorna verdadeiro se a imagem foi deletada
        }
        return false; // Retorna falso se a imagem não foi encontrada
    }
}
