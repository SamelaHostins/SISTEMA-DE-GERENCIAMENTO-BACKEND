package salao.online.application.dtos;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfissionalDTO extends InformacaoDTO{
    
    private UUID idProfissional;
    private UUID IdEndereco;
    private List<ServicoDTO> servicos;
    private List<EstoqueDTO> estoques;
}
