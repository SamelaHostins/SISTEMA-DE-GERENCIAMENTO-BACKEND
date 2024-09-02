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
public class ClienteDTO {
    
    private UUID idCliente;
    private String nome;
    private String sobrenome;
    private String nomeSocial;
    private short idade;
    private String email;
    private String telefone;
    private String usuario;
    private String senha;
    private List<AvaliacaoDTO> avaliacoes;
    private List<AgendamentoDTO> agendamentos;
}
