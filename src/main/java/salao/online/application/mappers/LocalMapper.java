package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.Named;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaLocalDTO;
import salao.online.domain.entities.Profissional;
import salao.online.domain.entities.Servico;

@Mapper(componentModel = "cdi")
public interface LocalMapper {

    @Named("mapProfissionalNome")
    default String mapProfissionalNome(Profissional profissional) {
        String[] partes = profissional.getSobrenome().trim().split("\\s+");
        String ultimoSobrenome = partes[partes.length - 1];
        return profissional.getNome() + " " + ultimoSobrenome;
    }

    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Mapping(source = "profissional", target = "nomeProfissional", qualifiedByName = "mapProfissionalNome")
    PesquisaLocalDTO fromEntityToPesquisaLocalDto(Servico entity);

    List<PesquisaLocalDTO> fromEntityListToPesquisaLocalDtoList(List<Servico> servicos);
}
