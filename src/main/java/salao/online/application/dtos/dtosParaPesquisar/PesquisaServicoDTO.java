package salao.online.application.dtos.dtosParaPesquisar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PesquisaServicoDTO {
    
    private UUID idProfissional;
    private UUID idServico;
    private String nomeServico;
    private String nomeProfissional;
}
