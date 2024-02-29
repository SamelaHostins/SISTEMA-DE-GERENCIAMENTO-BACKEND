package salao.online.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoDTO {
    
    private UUID idAvaliacao;
    private int nota;
    private LocalDate dataAvaliacao;
    private UUID idCliente;
    private UUID idServico;
}
