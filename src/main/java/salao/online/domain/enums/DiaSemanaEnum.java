package salao.online.domain.enums;

public enum DiaSemanaEnum {
    SEGUNDA(0),
    TERCA(1),
    QUARTA(2),
    QUINTA(3),
    SEXTA(4),
    SABADO(5),
    DOMINGO(6);

    private final int diaSemana;

    DiaSemanaEnum(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getValor() {
        return diaSemana;
    }
}
