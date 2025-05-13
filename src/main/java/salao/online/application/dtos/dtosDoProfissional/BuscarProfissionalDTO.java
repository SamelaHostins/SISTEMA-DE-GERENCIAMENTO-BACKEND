package salao.online.application.dtos.dtosDoProfissional;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salao.online.application.dtos.dtosDeEndereco.EnderecoDTO;
import salao.online.application.dtos.dtosDeImagem.ImagensDoProfissionalDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;
import salao.online.application.dtos.dtosHorario.HorarioTrabalhoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//Classe para que o profissional possa ver seu cadastro
public class BuscarProfissionalDTO {
    
    private UUID idProfissional;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private ProfissaoEsteticaEnumDTO profissao;
    private String descricaoDaProfissao;
    private String usuario;
    private String instagram;
    private EnderecoDTO endereco; 
    private List<ServicoDTO> servicos;
    private List<ImagensDoProfissionalDTO> imagens; 
    private List<HorarioTrabalhoDTO> horariosTrabalho;
}
