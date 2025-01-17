package salao.online.application.dtos.dtosDeImagem;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImagensDoClienteDTO {
    
    private String urlImagem;
    private UUID idCliente;
}
