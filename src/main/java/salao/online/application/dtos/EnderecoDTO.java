package salao.online.application.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnderecoDTO {
    
    private UUID idEndereco;
    private String rua;
    private String bairro;
    private String cidade;
    private int numero;
    private String cep;
}
