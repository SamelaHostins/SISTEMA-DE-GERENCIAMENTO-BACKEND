package salao.online.application.dtos.dtosDeCliente;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.application.dtos.dtosDeImagem.ImagensDoClienteDTO;
import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.application.dtos.AvaliacaoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// Classe para que o cliente possa ver seu cadastro
public class BuscarClienteDTO {

    private UUID idCliente;
    private String nome;
    private String sobrenome;
    private String usuario;
    private LocalDate dataNascimento; 
    private String email;
    private String telefone;
    private String documento; 
    private List<AvaliacaoDTO> avaliacoes;
    private List<AgendamentoDTO> agendamentos;
    private List<ImagensDoClienteDTO> imagens;
}
