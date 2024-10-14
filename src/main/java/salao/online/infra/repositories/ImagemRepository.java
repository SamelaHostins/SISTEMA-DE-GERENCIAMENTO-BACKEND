package salao.online.infra.repositories;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import salao.online.domain.entities.Imagem;

public interface ImagemRepository extends PanacheRepositoryBase<Imagem, UUID> {

    public void salvarImagem(Imagem imagem);

    public void atualizarImagem(UUID id, Imagem novaImagem);
}