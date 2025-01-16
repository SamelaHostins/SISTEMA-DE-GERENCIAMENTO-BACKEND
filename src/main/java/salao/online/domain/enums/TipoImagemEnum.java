package salao.online.domain.enums;

public enum TipoImagemEnum {
    PERFIL(0),
    PORT(1);

    private final int tipoImagem;

    TipoImagemEnum(int tipoImagem) {
        this.tipoImagem = tipoImagem;
    }

    public int getTipoImagem() {
        return tipoImagem;
    }

    // Método personalizado para buscar o enum pelo valor inteiro
    public static TipoImagemEnum fromTipoImagem(int tipoImagem) {
        for (TipoImagemEnum tipo : TipoImagemEnum.values()) {
            if (tipo.getTipoImagem() == tipoImagem) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de imagem inválido: " + tipoImagem);
    }
}
