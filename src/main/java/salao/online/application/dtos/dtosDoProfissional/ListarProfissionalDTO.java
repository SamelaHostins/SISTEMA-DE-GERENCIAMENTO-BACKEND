package salao.online.application.dtos.dtosDoProfissional;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.application.dtos.dtosDeImagem.ImagensDoProfissionalDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//Classe para que o cliente possa ver o profissional e seus serviços
public class ListarProfissionalDTO {

    private UUID idProfissional;
    private String usuario;
    private String email;
    private String telefone;
    private List<ServicoDTO> servicos;
    private List<ImagensDoProfissionalDTO> imagens; 
    
}
