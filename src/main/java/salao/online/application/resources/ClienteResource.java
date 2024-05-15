package salao.online.application.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import salao.online.application.dtos.ClienteDTO;
import salao.online.application.services.interfaces.ClienteService;
import salao.online.domain.exceptions.ValidacaoException;

@Path("/cliente")
@Tag(name = "Endpoints do Cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {
    
    @Inject
    ClienteService clienteService;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ClienteResource.class);

    @Operation(summary = "Cadastrando um Cliente")
    @APIResponse(responseCode = "200", description = "Cliente criado com sucesso!")
    @APIResponse(responseCode = "500", description = "Ocorreu um erro na requisição.")
    @POST
    @Transactional
    @Path("/criar")
    public Response criarAluno(@RequestBody ClienteDTO dto) {
        try {
            LOG.info("Requisição recebida - Cadastrar Cliente");
            ClienteDTO clienteDTO = clienteService.cadastrarCliente(dto);
            return Response.status(200).entity(clienteDTO).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Buscando um cliente")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "404", description = "O cliente não foi encontrado")
    @GET
    @Path("/buscar/{id_cliente}")
    public Response buscarClientePorId(@PathParam("id_cliente") UUID idCliente)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Buscar o Cliente");
            ClienteDTO clienteDTO = clienteService.buscarClientePorId(idCliente);
            return Response.status(200).entity(clienteDTO).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Buscando todos os cliente")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @GET
    public Response buscarClientes() {
        try {
            LOG.info("Requisição recebida - Buscar o Cliente");
            List<ClienteDTO> clienteDTO = clienteService.buscarClientesPorNome();
            return Response.status(200).entity(clienteDTO).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Deletando o cadastro de um cliente")
    @APIResponse(responseCode = "200", description = "Cadastro deletado com sucesso!")
    @APIResponse(responseCode = "404", description = "O cliente não foi encontrado")
    @DELETE
    @Transactional
    @Path("/deletar/{id_cliente}")
    public Response deletarCadastroCliente(@PathParam("id_cliente") UUID idCliente)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Deletar o cadastro do Cliente");
            ClienteDTO clienteDTO = clienteService.deletarCadastroCliente(idCliente);
            return Response.status(200).entity(clienteDTO).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Atualizando o cadastro de um cliente")
    @APIResponse(responseCode = "200", description = "Cadastro atualizado com sucesso!")
    @APIResponse(responseCode = "404", description = "O cliente não foi encontrado")
    @PUT
    @Transactional
    @Path("/atualizar/{id_cliente}")
    public Response atualizarCadastroCliente(@RequestBody ClienteDTO clienteDTO)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Atualizar o cadastro do Cliente");
            ClienteDTO clienteAtualizado = clienteService.atualizarCadastroCliente(clienteDTO);
            return Response.status(200).entity(clienteAtualizado).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }


}