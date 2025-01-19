package salao.online.application.resources;

import java.util.List;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.CriarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ProfissionalDTO;
import salao.online.application.services.interfaces.ProfissionalService;
import salao.online.domain.exceptions.ValidacaoException;

@Path("/profissional")
@Tag(name = "Endpoints do Profissional")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfissionalResource {

    @Inject
    ProfissionalService profissionalService;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ProfissionalResource.class);

    @Operation(summary = "Cadastrando um Profissional")
    @APIResponse(responseCode = "200", description = "Profissional criado com sucesso!")
    @APIResponse(responseCode = "500", description = "Ocorreu um erro na requisição.")
    @POST
    @Transactional
    @Path("/cadastrar")
    public Response cadastrarProfissional(@Valid @RequestBody CriarProfissionalDTO dto) {
        try {
            LOG.info("Requisição recebida - Cadastrar Profissional");
            CriarProfissionalDTO profissionalDTO = profissionalService.cadastrarProfissional(dto);
            return Response.status(200).entity(profissionalDTO).build();
        } catch (Exception ex) {
            LOG.error("Erro ao cadastrar profissional: {}", ex.getMessage(), ex);
            return Response.status(500).entity("Erro ao cadastrar profissional: " + ex.getMessage()).build();
        }
    }

    @Operation(summary = "Buscando um todos os dados de um Profissional")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "404", description = "O Profissional não foi encontrado")
    @GET
    @Path("/buscar/{id_profissional}")
    public Response buscarProfissionalPorId(@PathParam("id_profissional") UUID idProfissional)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Buscar o Profissional");
            BuscarProfissionalDTO profissionalDTO = profissionalService.buscarProfissionalPorId(idProfissional);
            return Response.status(200).entity(profissionalDTO).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Lista os dados básicos do Profissional")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "404", description = "O Profissional não foi encontrado")
    @GET
    @Path("/listar/{id_profissional}")
    public Response listarProfissionalPorId(@PathParam("id_profissional") UUID idProfissional)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Listar o Profissional");
            ListarProfissionalDTO profissionalDTO = profissionalService.listarProfissionalPorId(idProfissional);
            return Response.status(200).entity(profissionalDTO).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Listar todos os profissionais", description = "Retorna uma lista de todos os profissionais cadastrados com seus serviços e imagens.")
    @APIResponse(responseCode = "200", description = "Lista de profissionais retornada com sucesso.")
    @APIResponse(responseCode = "500", description = "Erro ao processar a requisição.")
    @GET
    @Path("/listar")
    public Response listarProfissionais() {
        try {
            LOG.info("Requisição recebida - Listar todos os profissionais");
            List<ListarProfissionalDTO> profissionais = profissionalService.listarTodosProfissionais();
            return Response.ok(profissionais).build();
        } catch (Exception ex) {
            LOG.error("Erro ao listar profissionais: {}", ex.getMessage(), ex);
            return Response.status(500).entity("Ocorreu um erro ao listar profissionais.").build();
        }
    }

    @Operation(summary = "Deletando o cadastro de um Profissional")
    @APIResponse(responseCode = "200", description = "Cadastro deletado com sucesso!")
    @APIResponse(responseCode = "404", description = "O Profissional não foi encontrado")
    @DELETE
    @Transactional
    @Path("/deletar/{id_profissional}")
    public Response deletarProfissional(@PathParam("id_profissional") UUID idProfissional)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Deletar o cadastro do Profissional");
            profissionalService.deletarProfissional(idProfissional);
            return Response.status(200).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    // ainda não está pronto, mas pode add ele no front
    @Operation(summary = "Atualizando o cadastro de um Profissional")
    @APIResponse(responseCode = "200", description = "Cadastro atualizado com sucesso!")
    @APIResponse(responseCode = "404", description = "O Profissional não foi encontrado")
    @PUT
    @Transactional
    @Path("/atualizar/{id_profissional}")
    public Response atualizarProfissional(@RequestBody ProfissionalDTO profissionalDTO)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Atualizar o cadastro do Profissional");
            ProfissionalDTO profissional = profissionalService.atualizarProfissional(profissionalDTO);
            return Response.status(200).entity(profissional).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

}
