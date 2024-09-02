package salao.online.application.dtos.dtosDoProfissional;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.application.dtos.dtosDoEstoque.EstoqueDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfissionalDTO{
    
    private UUID idProfissional;
    private String nome;
    private String sobrenome;
    private short idade;
    private String email;
    private String telefone;
    private String usuario;
    private String rua;
    private String bairro;
    private String cidade;
    private int numero;
    private String cep;
    private String senha;
    private UUID idEndereco;
    private List<ServicoDTO> servicos;
    private List<EstoqueDTO> estoques;
}
