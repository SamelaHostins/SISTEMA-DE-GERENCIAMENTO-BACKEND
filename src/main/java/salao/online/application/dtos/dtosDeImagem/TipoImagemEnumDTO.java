package salao.online.application.dtos.dtosDeImagem;

import lombok.Getter;
import java.util.Arrays;

public enum TipoImagemEnumDTO {

    PERFIL(0),
    PORT(1);

    public @Getter int tipoImagem;

    private TipoImagemEnumDTO(int tipoImagem) {
        this.tipoImagem = tipoImagem;
    }

    // Método para converter um valor inteiro para o enum
    public static TipoImagemEnumDTO fromTipoImagem(int tipoImagem) {
        return Arrays.stream(TipoImagemEnumDTO.values())
                .filter(t -> t.getTipoImagem() == tipoImagem)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de imagem inválido: " + tipoImagem));
    }
}
