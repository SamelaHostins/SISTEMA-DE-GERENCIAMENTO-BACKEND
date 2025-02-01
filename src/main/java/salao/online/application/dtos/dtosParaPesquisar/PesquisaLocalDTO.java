package salao.online.application.dtos.dtosParaPesquisar;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PesquisaLocalDTO {

    private UUID idProfissional;
    private String cidade;
    private String bairro;
    private String nomeProfissional;
    private String profissao;

}
