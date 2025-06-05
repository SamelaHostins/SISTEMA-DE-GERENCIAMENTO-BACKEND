package salao.online.application.dtos.dtosDoProfissional;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriarProfissionalDTO {

    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private int profissao;
    private String senha;
    private String documento;
    private Boolean aceitouTermos;
}
