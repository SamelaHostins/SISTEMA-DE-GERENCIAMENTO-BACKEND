package salao.online.application.dtos.dtosDoServico;

import java.util.Arrays;

import lombok.Getter;

public enum TipoServicoEnumDTO {

    NORMAL(0),
    ESPECIAL(1);

    private @Getter int tipoServico;

    private TipoServicoEnumDTO(int tipoServico) {
        this.tipoServico = tipoServico;
    }

    // Método para converter um valor inteiro para o enum
    public static TipoServicoEnumDTO fromTipoServico(int tipoServico) {
        return Arrays.stream(TipoServicoEnumDTO.values())
                .filter(t -> t.getTipoServico() == tipoServico)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de servico inválido: " + tipoServico));
    }
}
