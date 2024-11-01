package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.dtosDoServico.AtualizarServicoDTO;
import salao.online.application.dtos.dtosDoServico.CriarServicoDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;
import salao.online.domain.entities.Servico;

@Mapper(componentModel = "cdi", uses = AvaliacaoMapper.class)

public interface ServicoMapper {

    @InheritInverseConfiguration
    Servico toEntity(ServicoDTO dto);

    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Named("mapToDTO")
    ServicoDTO toDto(Servico entity);

    CriarServicoDTO toDtoCriar(Servico entity);

    Servico criarDtoToEntity(CriarServicoDTO dto);

    AtualizarServicoDTO toDtoAtualizar(Servico entity);

    @IterableMapping(qualifiedByName = "mapToDTO")
    @Named("mapListToDtoList")
    List<ServicoDTO> toDtoList(List<Servico> servicos);

}
