package salao.online.application.dtos.dtosDoProfissional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListarProfissionalEmDestaqueDTO {
    private UUID idProfissional;
    private String nome;
    private String profissao;
    private String cidade;
    private String instagram;
    private String estado;
    private String descricaoDaProfissao;
    private String urlImagem;
}