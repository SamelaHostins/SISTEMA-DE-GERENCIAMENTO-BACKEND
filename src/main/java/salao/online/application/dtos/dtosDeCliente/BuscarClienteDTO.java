package salao.online.application.dtos.dtosDeCliente;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.application.dtos.AgendamentoDTO;
import salao.online.application.dtos.AvaliacaoDTO;
import salao.online.application.dtos.dtosDeImagem.ImagensDoClienteDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//Classe para que o cliente possa ver seu cadastro
public class BuscarClienteDTO {
    
    private UUID idCliente;
    private String nome;
    private String sobrenome;
    private String usuario;
    private short idade;
    private String email;
    private String telefone;
    private List<AvaliacaoDTO> avaliacoes;
    private List<AgendamentoDTO> agendamentos;
    private List<ImagensDoClienteDTO> imagens;
}
