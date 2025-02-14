package salao.online.application.dtos.dtosDoProfissional;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.application.dtos.EnderecoDTO;
import salao.online.application.dtos.dtosDeImagem.ImagensDoProfissionalDTO;
import salao.online.application.dtos.dtosDoEstoque.EstoqueDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//Classe para que o profissional possa ver seu cadastro
public class BuscarProfissionalDTO {
    
    private UUID idProfissional;
    private String instagram;
    private String profissao;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String usuario;
    private String senha;
    private String documento; 
    private EnderecoDTO endereco; 
    private List<ServicoDTO> servicos;
    private List<EstoqueDTO> estoques;
    private List<ImagensDoProfissionalDTO> imagens; 
}
