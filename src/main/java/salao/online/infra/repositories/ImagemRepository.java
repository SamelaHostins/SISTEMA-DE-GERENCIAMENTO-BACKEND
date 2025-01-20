package salao.online.infra.repositories;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import salao.online.domain.entities.Imagem;
import salao.online.domain.enums.TipoImagemEnum;

public interface ImagemRepository extends PanacheRepositoryBase<Imagem, UUID> {

    public void salvarImagem(Imagem imagem);

    public void atualizarImagem(UUID id, Imagem novaImagem);

    public Imagem buscarFoto(UUID idUsuario, TipoImagemEnum tipoImagem);

    public List<Imagem> buscarFotos(UUID idUsuario, TipoImagemEnum tipoImagem);
}