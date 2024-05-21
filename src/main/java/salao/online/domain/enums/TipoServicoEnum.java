package salao.online.domain.enums;

import lombok.Getter;

public enum TipoServicoEnum {
    NORMAL(0),
    ESPECIAL(1);

    public @Getter int tipoServico;

    private TipoServicoEnum(int tipoServico) {
        this.tipoServico = tipoServico;
    }
}
