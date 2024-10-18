package salao.online.domain.enums;

import lombok.Getter;

public enum TipoImagemEnum {
    PERFIL(0),
    PORT(1);

    public @Getter int tipoImagem;

    private TipoImagemEnum(int tipoImagem) {
        this.tipoImagem = tipoImagem;
    }
}

