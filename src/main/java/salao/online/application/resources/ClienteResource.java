package salao.online.application.resources;

import java.util.List;
import java.util.Map;
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

import salao.online.application.dtos.dtosDeCliente.AtualizarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.BuscarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.CriarClienteDTO;
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
    @Path("/cadastrar")
    public Response cadastrarCliente(@Valid @RequestBody CriarClienteDTO dto) {
        try {
            LOG.info("Requisição recebida - Cadastrar Cliente");
            CriarClienteDTO clienteDTO = clienteService.cadastrarCliente(dto);
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
            BuscarClienteDTO clienteDTO = clienteService.buscarClientePorId(idCliente);
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
            List<BuscarClienteDTO> clienteDTO = clienteService.buscarClientesPorNome();
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
    public Response deletarCliente(@PathParam("id_cliente") UUID idCliente)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Deletar o cadastro do Cliente");
            clienteService.deletarCliente(idCliente);
            return Response.status(200).build();
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
    public Response atualizarCliente(@RequestBody AtualizarClienteDTO clienteDTO)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Atualizar o cadastro do Cliente");
            AtualizarClienteDTO clienteAtualizado = clienteService.atualizarCliente(clienteDTO);
            return Response.status(200).entity(clienteAtualizado).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Buscando as faixas etarias das clientes cadastradas")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "404", description = "O cliente não foi encontrado")
    @PUT
    @Path("/especial/{id_cliente}")
    public Response atualizarClienteEspecial(@PathParam("id_cliente") UUID idCliente) throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Atualizar o cliente para especial");
            boolean clienteAtualizado = clienteService.atualizarClienteEspecial(idCliente);
            return Response.status(200).entity(clienteAtualizado).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Buscando as faixas etarias das clientes cadastradas")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @GET
    @Path("/faixas-etarias")
    public Response obterFaixasEtariasDasClientes() {
        try {
            LOG.info("Requisição recebida - Buscar as faixas etarias das clientes cadastradas");
            Map<String, Integer> faixasEtarias = clienteService.obterFaixasEtariasDasClientes();
            return Response.status(200).entity(faixasEtarias).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }
}
