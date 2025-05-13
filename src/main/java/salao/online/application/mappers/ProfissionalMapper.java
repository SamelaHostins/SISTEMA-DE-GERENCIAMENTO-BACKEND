package salao.online.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.dtosDeEndereco.BuscarEnderecoDoProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.AtualizarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalAutenticadoDTO;
import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.CriarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalEmDestaqueDTO;
import salao.online.application.dtos.dtosDoProfissional.ProfissaoEsteticaEnumDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaProfissionalDTO;
import salao.online.domain.entities.Profissional;
import salao.online.domain.enums.ProfissaoEsteticaEnum;

@Mapper(componentModel = "cdi", uses = { EstoqueMapper.class, ServicoMapper.class, ImagemMapper.class,
        AgendamentoMapper.class, AvaliacaoMapper.class, EnderecoMapper.class, HorarioTrabalhoMapper.class })
public interface ProfissionalMapper {

    @Named("intToProfissaoEnum")
    default salao.online.domain.enums.ProfissaoEsteticaEnum intToProfissaoEnum(int valor) {
        return salao.online.domain.enums.ProfissaoEsteticaEnum.fromProfissao(valor);
    }

    @Named("profissaoEnumToInt")
    default int profissaoEnumToInt(salao.online.domain.enums.ProfissaoEsteticaEnum profissao) {
        return profissao.getProfissao();
    }

    @Named("dtoToEnum")
    default ProfissaoEsteticaEnum dtoToEnum(ProfissaoEsteticaEnumDTO dto) {
        return ProfissaoEsteticaEnum.fromProfissao(dto.getValor());
    }

    @Named("enumToDto")
    default String enumToDto(ProfissaoEsteticaEnum entity) {
        return ProfissaoEsteticaEnumDTO.fromValor(entity.getProfissao()).getDescricao();
    }

    @Mapping(source = "profissao", target = "profissao", qualifiedByName = "enumToDto")
    CriarProfissionalDTO fromEntityToCriarDto(Profissional entity);

    @Mapping(target = "idProfissional", ignore = true)
    @Mapping(target = "dataNascimento", source = "dto.dataNascimento")
    @Mapping(target = "endereco", source = "dto.endereco")
    @Mapping(source = "profissao", target = "profissao", qualifiedByName = "dtoToEnum")
    Profissional fromCriarDtoToEntity(CriarProfissionalDTO dto);

    BuscarProfissionalDTO fromEntityToBuscarDto(Profissional entity);

    @Mapping(source = "idProfissional", target = "idProfissional")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "sobrenome", target = "sobrenome")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telefone", target = "telefone")
    @Mapping(source = "senha", target = "senha")
    @Mapping(source = "instagram", target = "instagram")
    @Mapping(source = "profissao", target = "profissao", qualifiedByName = "profissaoEnumToInt")
    @Mapping(source = "horariosTrabalho", target = "horariosTrabalho")
    AtualizarProfissionalDTO fromEntityToAtualizarDto(Profissional entity);

    @Mapping(target = "idProfissional", ignore = true)
    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(source = "imagens", target = "imagens")
    ListarProfissionalDTO fromEntityToListarDto(Profissional entity);

    @Named("mapNomeCompleto")
    default String mapNomeCompleto(Profissional profissional) {
        return profissional.getNome() + " " + profissional.getSobrenome();
    }

    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = ".", target = "nome", qualifiedByName = "mapNomeCompleto")
    PesquisaProfissionalDTO fromEntityToPesquisaDto(Profissional entity);

    @Mapping(source = "idProfissional", target = "idProfissional")
    @Mapping(source = "endereco", target = "endereco")
    BuscarEnderecoDoProfissionalDTO fromEntityToBuscarEnderecoDto(Profissional entity);

    @Mapping(source = "idProfissional", target = "idProfissional")
    @Mapping(source = "email", target = "email")
    BuscarProfissionalAutenticadoDTO toAutenticadoDto(Profissional entity);

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "profissao", target = "profissao", qualifiedByName = "enumToDto")
    @Mapping(source = "endereco.cidade", target = "cidade")
    @Mapping(source = "endereco.estado", target = "estado")
    @Mapping(source = "instagram", target = "instagram")
    @Mapping(source = "descricaoDaProfissao", target = "descricaoDaProfissao")
    ListarProfissionalEmDestaqueDTO fromEntityToDestaqueDto(Profissional profissional);

}
