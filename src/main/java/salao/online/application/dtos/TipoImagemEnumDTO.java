package salao.online.application.dtos;

import lombok.Getter;

public enum TipoImagemEnumDTO {

    PERFIL(0),
    PORT(1);

    public @Getter int tipoImagem;

    private TipoImagemEnumDTO(int tipoImagem) {
        this.tipoImagem = tipoImagem;
    }
}
