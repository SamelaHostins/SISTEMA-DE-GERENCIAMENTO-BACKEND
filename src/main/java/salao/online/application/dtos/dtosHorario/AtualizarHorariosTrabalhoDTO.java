package salao.online.application.dtos.dtosHorario;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarHorariosTrabalhoDTO {

    @NotEmpty
    private List<HorarioTrabalhoDTO> horarios;
}