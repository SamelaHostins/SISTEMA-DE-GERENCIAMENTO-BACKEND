package salao.online.application.dtos;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteDTO{
    
    private UUID idCliente;
    private String nome;
    private String sobrenome;
    private int idade;
    private String email;
    private String telefone;
    private String usuario;
    private String senha;
    private List<AvaliacaoDTO> avaliacoes;
    private List<AgendamentoDTO> agendamentos;
}
