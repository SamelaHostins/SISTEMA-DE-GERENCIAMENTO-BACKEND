package salao.online.infra.repositoriesImpl;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
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
    public Imagem buscarFoto(UUID idUsuario, TipoImagemEnum tipoImagem) {
        String query = "tipoImagem = :tipoImagem AND " +
                "(profissional.idProfissional = :idUsuario OR cliente.idCliente = :idUsuario)";
        return find(query,
                Parameters.with("idUsuario", idUsuario)
                        .and("tipoImagem", tipoImagem))
                .firstResult();
    }

    @Override
    public List<Imagem> buscarFotos(UUID idUsuario, TipoImagemEnum tipoImagem) {
        String query = "tipoImagem = :tipoImagem AND " +
                "(profissional.idProfissional = :idUsuario OR cliente.idCliente = :idUsuario)";
        return find(query,
                Parameters.with("idUsuario", idUsuario)
                        .and("tipoImagem", tipoImagem))
                .list();
    }
}
