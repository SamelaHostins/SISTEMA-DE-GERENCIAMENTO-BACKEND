package salao.online.infra.repositoriesImpl;

import java.util.UUID;

import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import salao.online.domain.entities.Imagem;
import salao.online.domain.enums.TipoImagemEnum;
import salao.online.infra.repositories.ImagemRepository;

@ApplicationScoped
public class ImagemRepositoryImpl implements ImagemRepository {

    @Inject
    ImagemRepository imagemRepository;

    public void salvarImagem(Imagem imagem) {
        persist(imagem);
    }

    public void atualizarImagem(UUID id, Imagem novaImagem) {
        Imagem imagemExistente = imagemRepository.findById(id);
        if (imagemExistente != null) {
            imagemExistente.setUrlImagem(novaImagem.getUrlImagem());
            persist(imagemExistente);
        }
    }

    @Override
    public boolean existeFotoDePerfil(UUID idUsuario, boolean ehProfissional) {
        String query = ehProfissional
                ? "profissional.idProfissional = :idUsuario AND tipoImagem = :tipoImagem"
                : "cliente.idCliente = :idUsuario AND tipoImagem = :tipoImagem";

        return find(query,
                Parameters.with("idUsuario", idUsuario)
                        .and("tipoImagem", TipoImagemEnum.PERFIL))
                .count() > 0;
    }

}
