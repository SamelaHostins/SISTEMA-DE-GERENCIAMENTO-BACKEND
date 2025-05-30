package salao.online.application.resources;

import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import salao.online.application.dtos.dtosDoServico.AtualizarServicoDTO;
import salao.online.application.dtos.dtosDoServico.BuscarServicoDTO;
import salao.online.application.dtos.dtosDoServico.CriarServicoDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaLocalDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaServicoDTO;
import salao.online.application.services.interfaces.ServicoService;
import salao.online.domain.exceptions.ValidacaoException;

@Path("/servico")
@Tag(name = "Endpoints do Servico")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicoResource {

    @Inject
    ServicoService servicoService;

    @Inject
    JsonWebToken jwt;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ServicoResource.class);

    @POST
    @Transactional
    @Path("/cadastrar")
    @RolesAllowed("PROFISSIONAL")
    @Operation(summary = "Cadastrando um Servico")
    @APIResponse(responseCode = "200", description = "Servico criado com sucesso!")
    @APIResponse(responseCode = "500", description = "Ocorreu um erro na requisição.")
    public Response cadastrarServico(CriarServicoDTO dto, @Context SecurityContext securityContext) {
        try {
            LOG.info("Requisição recebida - Cadastrar Servico");

            UUID idProfissional = UUID.fromString(jwt.getSubject());
            dto.setIdProfissional(idProfissional);

            CriarServicoDTO servicoDTO = servicoService.cadastrarServico(dto);
            return Response.status(200).entity(servicoDTO).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Atualizar serviço", description = "Atualiza as informações de um serviço existente.")
    @APIResponse(responseCode = "200", description = "Serviço atualizado com sucesso!")
    @APIResponse(responseCode = "404", description = "Serviço não encontrado.")
    @APIResponse(responseCode = "400", description = "Requisição inválida.")
    @APIResponse(responseCode = "500", description = "Erro interno no servidor.")
    @PUT
    @Path("/atualizar/{id_servico}")
    @RolesAllowed("PROFISSIONAL")
    @Transactional
    public Response atualizarServico(
            @PathParam("id_servico") UUID idServico,
            AtualizarServicoDTO servicoDTO) {

        try {

            UUID idProfissional = UUID.fromString(jwt.getSubject());
            servicoDTO.setIdProfissional(idProfissional);
            LOG.info("Recebendo solicitação para atualizar o serviço com ID: " + idServico);
            AtualizarServicoDTO servicoAtualizado = servicoService.atualizarServico(servicoDTO);

            LOG.info(String.format("Serviço com ID: %s atualizado com sucesso.", idServico));
            return Response.ok(servicoAtualizado).build();

        } catch (ValidacaoException ex) {
            LOG.error(String.format("Erro de validação ao atualizar serviço: %s", ex.getMessage()));
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ex.getMessage())
                    .build();

        } catch (Exception ex) {
            LOG.error("Erro ao atualizar serviço: ", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Ocorreu um erro ao processar a solicitação.")
                    .build();
        }
    }

    @Operation(summary = "Verifica se o serviço possui agendamentos")
    @GET
    @Path("/possui-agendamentos/{idServico}")
    @RolesAllowed("PROFISSIONAL")
    public Response possuiAgendamentos(@PathParam("idServico") UUID idServico) {
        try {
            boolean possui = servicoService.possuiAgendamentos(idServico);
            return Response.ok(possui).build();
        } catch (ValidacaoException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Operation(summary = "Deletando um servico")
    @APIResponse(responseCode = "200", description = "Cadastro deletado com sucesso!")
    @APIResponse(responseCode = "404", description = "O servico não foi encontrado")
    @DELETE
    @Transactional
    @Path("/deletar/{id_servico}")
    @RolesAllowed("PROFISSIONAL")
    public Response deletarServico(@PathParam("id_servico") UUID idservico)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Deletar o cadastro do servico");
            servicoService.deletarCadastroServico(idservico);
            return Response.noContent().build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Listando os serviços de um profissional", description = "Lista os serviços normais se não indicado nenhum tipo de serviço ou indicado "
            +
            "o tipo de serviço normal. Se indicado o tipo de serviço especial, listará os serviços especiais.")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @GET
    @PermitAll
    @Path("/listar-servicos/{id_profissional}/{tipo_servico}")
    public Response listarServicosDoProfissional(
            @PathParam("id_profissional") UUID idProfissional,
            @PathParam("tipo_servico") int tipoServico) {
        try {
            LOG.info("Requisição recebida - listar os serviços de um profissional");

            List<ServicoDTO> servicos = servicoService.listarServicosDoProfissional(idProfissional, tipoServico);

            return Response.status(200).entity(servicos).build();
        } catch (IllegalArgumentException ex) {
            LOG.error("Erro de validação no tipo de serviço", ex);
            return Response.status(400).entity("Tipo de serviço inválido.").build();
        } catch (Exception ex) {
            LOG.error("Erro ao listar serviços do profissional", ex);
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Listando todos os serviços de um profissional", description = "Lista tanto os serviços especiais quanto os normais.")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @GET
    @PermitAll
    @Path("/todos-os-servicos/{id_profissional}")
    public Response listarTodosOsServicosDoProfissional(
            @PathParam("id_profissional") UUID idProfissional) {

        try {
            LOG.info("Requisição recebida - listar todos os serviços de um profissional");
            List<ServicoDTO> servicos = servicoService.listarTodosOsServicosDoProfissional(idProfissional);
            return Response.status(200).entity(servicos).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @GET
    @PermitAll
    @Path("/pesquisar")
    @Operation(summary = "Listar todos os serviços disponíveis", description = "Retorna todos os serviços cadastrados por profissionais, incluindo informações como nome, tempo de duração e valor.")
    @APIResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso.")
    @APIResponse(responseCode = "500", description = "Erro interno ao buscar os serviços.")
    public Response pesquisaTodosServicos() {
        try {
            LOG.info("Requisição recebida - listar todos os serviços disponíveis");
            List<PesquisaServicoDTO> servicos = servicoService.pesquisarTodosServicos();
            return Response.ok(servicos).build();
        } catch (Exception ex) {
            LOG.error("Erro ao buscar serviços:", ex);
            return Response.status(500).entity("Ocorreu um erro ao buscar os serviços.").build();
        }
    }

    @GET
    @PermitAll
    @Path("/pesquisarLocal")
    @Operation(summary = "Listar locais com serviços disponíveis", description = "Retorna uma lista de locais (cidade e bairro) em que há profissionais com imagem de perfil e ao menos um serviço cadastrado.")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    public Response pesquisarTodasAsCidadesComServicos() {
        try {
            LOG.info("Requisição recebida - listar locais com serviços disponíveis");
            List<PesquisaLocalDTO> locais = servicoService.pesquisarTodasAsCidadesComServicos();
            return Response.ok(locais).build();
        } catch (Exception ex) {
            LOG.error("Erro ao listar locais com serviços: ", ex);
            return Response.status(500).entity("Ocorreu um erro ao buscar os locais.").build();
        }
    }

    @Operation(summary = "Busca o servico pelo ID")
    @GET
    @Path("/buscar/{idServico}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarServicoPorId(@PathParam("idServico") UUID idServico) throws ValidacaoException {
        try {
            ServicoDTO dto = servicoService.buscarServicoPorId(idServico);
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Operation(summary = "Buscar serviço com informações básicas")
    @GET
    @Path("/buscar-basico/{idServico}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarServicoBasicoPorId(@PathParam("idServico") UUID idServico) {
        try {
            BuscarServicoDTO dto = servicoService.buscarServicoBasicoPorId(idServico);
            return Response.ok(dto).build();
        } catch (ValidacaoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar o serviço básico.").build();
        }
    }
}
