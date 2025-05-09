package salao.online.domain.enums;

import lombok.Getter;

public enum ProfissaoEsteticaEnum {
    BARBEIRA(0),
    BARBEIRO(1),
    CABELEIREIRA(2),
    CABELEIREIRO(3),
    CONSULTOR_DE_BELEZA(4),
    COSMETOLOGA(5),
    COSMETOLOGO(6),
    DEPILADORA(7),
    DEPILADOR(8),
    DESIGN_DE_UNHAS(9),
    DESIGNER_DE_CILIOS(10),
    DESIGNER_DE_MICROPIGMENTACAO(11),
    DESIGNER_DE_SOBRANCELHAS(12),
    DERMATOLOGISTA(13),
    ESTETICISTA(14),
    LASH_DESIGNER(15),
    MANICURE(16),
    MANICURE_E_PEDICURE(17),
    MASSAGISTA(18),
    MASSOTERAPEUTA(19),
    MAQUIADORA(20),
    MAQUIADOR(21),
    MICROPIGMENTADORA(22),
    MICROPIGMENTADOR(23),
    PEDICURE(24),
    PODOLOGA(25),
    PODOLOGO(26),
    PROFISSIONAL_DE_VISAGISMO(27),
    TERAPEUTA_CAPILAR(28);

    public @Getter int profissao;

    private ProfissaoEsteticaEnum(int profissao) {
        this.profissao = profissao;
    }

    public static ProfissaoEsteticaEnum fromProfissao(int profissao) {
        for (ProfissaoEsteticaEnum p : values()) {
            if (p.getProfissao() == profissao)
                return p;
        }
        throw new IllegalArgumentException("Profissão inválida: " + profissao);
    }
}
