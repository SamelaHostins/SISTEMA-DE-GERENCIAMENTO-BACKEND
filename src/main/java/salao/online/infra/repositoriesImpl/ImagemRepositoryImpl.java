package salao.online.infra.repositoriesImpl;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import salao.online.domain.entities.Imagem;
import salao.online.infra.repositories.ImagemRepository;

@ApplicationScoped
public class ImagemRepositoryImpl implements ImagemRepository{
    
    @Inject
    ImagemRepository imagemRepository;

    public void salvarImagem(Imagem imagem) {
        imagemRepository.persist(imagem); 
    }

    public void atualizarImagem(UUID id, Imagem novaImagem) {
        Imagem imagemExistente = imagemRepository.findById(id); 
        if (imagemExistente != null) {
            imagemExistente.setUrlImagem(novaImagem.getUrlImagem()); 
            imagemRepository.persist(imagemExistente); 
        }
    }
}
