package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.dtosHorario.HorarioTrabalhoDTO;
import salao.online.domain.entities.HorarioTrabalho;
import salao.online.domain.enums.DiaSemanaEnum;

@Mapper(componentModel = "cdi")
public interface HorarioTrabalhoMapper {

    @Named("diaSemanaEnumToInt")
    default Integer diaSemanaEnumToInt(DiaSemanaEnum dia) {
        return dia.getValor();
    }

    @Named("intToDiaSemanaEnum")
    default DiaSemanaEnum intToDiaSemanaEnum(Integer valor) {
        for (DiaSemanaEnum dia : DiaSemanaEnum.values()) {
            if (dia.getValor() == valor) {
                return dia;
            }
        }
        throw new IllegalArgumentException("Valor inválido para DiaSemanaEnum: " + valor);
    }    
    
    @Mapping(source = "diaSemana", target = "diaSemana", qualifiedByName = "diaSemanaEnumToInt")
    HorarioTrabalhoDTO fromEntityToDto(HorarioTrabalho entity);

    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "diaSemana", target = "diaSemana", qualifiedByName = "intToDiaSemanaEnum")
    HorarioTrabalho fromDtoToEntity(HorarioTrabalhoDTO dto);

    List<HorarioTrabalhoDTO> fromEntityListToDtoList(List<HorarioTrabalho> entities);

    List<HorarioTrabalho> fromDtoListToEntityList(List<HorarioTrabalhoDTO> dtos);
}
