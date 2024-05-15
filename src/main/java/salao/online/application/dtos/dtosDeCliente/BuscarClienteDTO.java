package salao.online.application.dtos.dtosDeCliente;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.application.dtos.AgendamentoDTO;
import salao.online.application.dtos.AvaliacaoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuscarClienteDTO {
    
    private UUID idCliente;
    private String usuario;
    private int idade;
    private String email;
    private String telefone;
    private List<AvaliacaoDTO> avaliacoes;
    private List<AgendamentoDTO> agendamentos;
}
