package salao.online.application.dtos.dtosDoProfissional;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// Classe para que o cliente possa ver o profissional e seus serviços
public class ListarProfissionalDTO {

    private UUID idProfissional;
    private String nome;
    private String sobrenome;
    private String instagram;
    private ProfissaoEsteticaEnumDTO profissao;
    private LocalDate dataNascimento;
    private String descricaoDaProfissao;
    private String usuario;
    private String email;
    private String telefone;
    private String documento;
    private String senha;
}
