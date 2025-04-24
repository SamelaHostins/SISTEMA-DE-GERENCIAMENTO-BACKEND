package salao.online.application.dtos.dtosDoProfissional;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.application.dtos.EnderecoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuscarEnderecoDoProfissional {

    private UUID idProfissional;
    private EnderecoDTO endereco;
}
