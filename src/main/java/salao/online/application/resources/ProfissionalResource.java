package salao.online.application.resources;

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
            CriarProfissionalDTO ProfissionalDTO = profissionalService.cadastrarProfissional(dto);
            return Response.status(200).entity(ProfissionalDTO).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Buscando um Profissional")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "404", description = "O Profissional não foi encontrado")
    @GET
    @Path("/buscar/{id_profissional}")
    public Response buscarProfissionalPorId(@PathParam("id_profissional") UUID idProfissional)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Buscar o Profissional");
            BuscarProfissionalDTO ProfissionalDTO = profissionalService.buscarProfissionalPorId(idProfissional);
            return Response.status(200).entity(ProfissionalDTO).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Deletando o cadastro de um Profissional")
    @APIResponse(responseCode = "200", description = "Cadastro deletado com sucesso!")
    @APIResponse(responseCode = "404", description = "O Profissional não foi encontrado")
    @DELETE
    @Transactional
    @Path("/deletar/{id_profissional}")
    public Response deletarCadastroDoProfissional(@PathParam("id_profissional") UUID idProfissional)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Deletar o cadastro do Profissional");
            profissionalService.deletarCadastroDoProfissional(idProfissional);
            return Response.status(200).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Atualizando o cadastro de um Profissional")
    @APIResponse(responseCode = "200", description = "Cadastro atualizado com sucesso!")
    @APIResponse(responseCode = "404", description = "O Profissional não foi encontrado")
    @PUT
    @Transactional
    @Path("/atualizar/{id_profissional}")
    public Response atualizarCadastroProfissional(@RequestBody ProfissionalDTO ProfissionalDTO)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Atualizar o cadastro do Profissional");
            ProfissionalDTO ProfissionalAtualizado = profissionalService.atualizarCadastroDoProfissional(ProfissionalDTO);
            return Response.status(200).entity(ProfissionalAtualizado).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

}
