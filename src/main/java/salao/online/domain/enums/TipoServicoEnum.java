package salao.online.domain.enums;

import lombok.Getter;

public enum TipoServicoEnum {
    NORMAL(0),
    ESPECIAL(1);

    public @Getter int tipoServico;

    private TipoServicoEnum(int tipoServico) {
        this.tipoServico = tipoServico;
    }

    // Método personalizado para buscar o enum pelo valor inteiro
    public static TipoServicoEnum fromTipoServico(int tipoServico) {
        for (TipoServicoEnum tipo : TipoServicoEnum.values()) {
            if (tipo.getTipoServico() == tipoServico) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de serviço inválido: " + tipoServico);
    }
}
