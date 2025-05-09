package salao.online.application.dtos.dtosDoServico;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.application.dtos.AvaliacaoDTO;
import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServicoDTO {

    private UUID idServico;
    private String nome;
    private String especificacao;
    private String termosECondicoes;
    private String tempo;
    private double valor;
    private TipoServicoEnumDTO tipoServico;
    private UUID idProfissional;
    private List<AvaliacaoDTO> avaliacoes;
    private List<AgendamentoDTO> agendamentos;
}
