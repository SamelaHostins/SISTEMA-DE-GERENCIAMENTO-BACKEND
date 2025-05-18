package salao.online.application.dtos.dtosDoServico;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuscarServicoDTO {
    private UUID idServico;
    private String nome;
    private String tempo;
    private double valor;
    private String especificacao;
    private UUID idProfissional;
}
