package salao.online.application.dtos;

import lombok.Getter;

public enum TipoServicoEnumDTO {

    NORMAL(0),
    ESPECIAL(1);

    private @Getter int tipoServico;

    private TipoServicoEnumDTO(int tipoServico) {
        this.tipoServico = tipoServico;
    }
}
