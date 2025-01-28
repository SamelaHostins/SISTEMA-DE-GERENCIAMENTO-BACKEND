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
public class AtualizarServicoDTO {
    
    private UUID idServico;
    private String nome;
    private TipoServicoEnumDTO tipoServico;
    private String especificacao;
    private String termosECondicoes;
    private String tempo;
    private double valor;
    private UUID idProfissional;
}
