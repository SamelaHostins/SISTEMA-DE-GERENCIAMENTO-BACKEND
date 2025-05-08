package salao.online.application.dtos.dtosDeEndereco;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuscarEnderecoDoProfissionalDTO {

    private UUID idProfissional;
    private EnderecoDTO endereco;
}
