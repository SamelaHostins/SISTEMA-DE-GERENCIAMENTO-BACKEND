package salao.online.application.dtos.dtosDoProfissional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProfissaoEsteticaEnumDTO {
    BARBEIRA(0, "Barbeira"),
    BARBEIRO(1, "Barbeiro"),
    CABELEIREIRA(2, "Cabeleireira"),
    CABELEIREIRO(3, "Cabeleireiro"),
    CONSULTOR_DE_BELEZA(4, "Consultor de Beleza"),
    COSMETOLOGA(5, "Cosmetóloga"),
    COSMETOLOGO(6, "Cosmetólogo"),
    DEPILADORA(7, "Depiladora"),
    DEPILADOR(8, "Depilador"),
    DESIGN_DE_UNHAS(9, "Design de Unhas"),
    DESIGNER_DE_CILIOS(10, "Designer de Cílios"),
    DESIGNER_DE_MICROPIGMENTACAO(11, "Designer de Micropigmentação"),
    DESIGNER_DE_SOBRANCELHAS(12, "Designer de Sobrancelhas"),
    DERMATOLOGISTA(13, "Dermatologista"),
    ESTETICISTA(14, "Esteticista"),
    LASH_DESIGNER(15, "Lash Designer"),
    MANICURE(16, "Manicure"),
    MANICURE_E_PEDICURE(17, "Manicure e Pedicure"),
    MASSAGISTA(18, "Massagista"),
    MASSOTERAPEUTA(19, "Massoterapeuta"),
    MAQUIADORA(20, "Maquiadora"),
    MAQUIADOR(21, "Maquiador"),
    MICROPIGMENTADORA(22, "Micropigmentadora"),
    MICROPIGMENTADOR(23, "Micropigmentador"),
    PEDICURE(24, "Pedicure"),
    PODOLOGA(25, "Podóloga"),
    PODOLOGO(26, "Podólogo"),
    PROFISSIONAL_DE_VISAGISMO(27, "Profissional de Visagismo"),
    TERAPEUTA_CAPILAR(28, "Terapeuta Capilar");

    private final int valor;
    private final String descricao;

    public static ProfissaoEsteticaEnumDTO fromValor(int valor) {
        for (ProfissaoEsteticaEnumDTO p : values()) {
            if (p.getValor() == valor) return p;
        }
        throw new IllegalArgumentException("Profissão inválida: " + valor);
    }
}