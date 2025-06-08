package salao.online.application.resources;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import salao.online.application.dtos.dtosDeCliente.AlterarSenhaClienteDTO;
import salao.online.application.dtos.dtosDeCliente.AtualizarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.AtualizarPreferenciaHorarioDTO;
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

    @Inject
    JsonWebToken jwt;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ClienteResource.class);

    @POST
    @PermitAll
    @Transactional
    @Path("/cadastrar")
    @Operation(summary = "Cadastrando um Cliente")
    @APIResponse(responseCode = "201", description = "Cliente criado com sucesso!")
    @APIResponse(responseCode = "409", description = "E-mail ou documento já cadastrado.")
    @APIResponse(responseCode = "500", description = "Ocorreu um erro na requisição.")
    public Response cadastrarCliente(@Valid @RequestBody CriarClienteDTO dto) {
        try {
            LOG.info("Requisição recebida - Cadastrar Cliente");
            CriarClienteDTO clienteDTO = clienteService.cadastrarCliente(dto);
            return Response.status(Response.Status.CREATED).entity(clienteDTO).build();
        } catch (ValidacaoException ex) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("erro", ex.getMessage()))
                    .build();
        } catch (Exception ex) {
            LOG.error("Erro interno ao cadastrar cliente", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", "Ocorreu um erro na requisição."))
                    .build();
        }
    }

    @PUT
    @Path("/preferencias")
    @RolesAllowed({ "CLIENTE" })
    @Transactional
    @Operation(summary = "Atualiza preferência de horário do cliente")
    public Response atualizarPreferenciaHorario(AtualizarPreferenciaHorarioDTO dto) {
        try {
            AtualizarPreferenciaHorarioDTO atualizado = clienteService.atualizarPreferenciaHorario(dto);
            return Response.ok(atualizado).build();
        } catch (ValidacaoException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar preferência de horário.")
                    .build();
        }
    }

    @Operation(summary = "Buscar a preferência de horário do cliente logado")
    @APIResponse(responseCode = "200", description = "Preferência de horário retornada com sucesso!")
    @APIResponse(responseCode = "404", description = "O cliente não foi encontrado")
    @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    @GET
    @Path("/buscar-preferencias")
    @RolesAllowed("CLIENTE")
    public Response buscarPreferenciaHorario(@Context JsonWebToken jwt) {
        try {
            LOG.info("Requisição recebida - Buscar preferência de horário do Cliente logado");

            UUID idCliente = UUID.fromString(jwt.getSubject());
            AtualizarPreferenciaHorarioDTO preferencias = clienteService.buscarPreferenciaHorario(idCliente);

            // Sempre tem default no banco
            return Response.ok(preferencias).build();

        } catch (ValidacaoException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocorreu um erro na requisição.")
                    .build();
        }
    }

    @Operation(summary = "Buscar dados do cliente logado")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "404", description = "O cliente não foi encontrado")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @GET
    @Path("/buscar")
    @RolesAllowed("CLIENTE")
    public Response buscarClienteLogado(@Context JsonWebToken jwt) {
        try {
            LOG.info("Requisição recebida - Buscar Cliente logado");

            String idClienteStr = jwt.getSubject();
            UUID idCliente = UUID.fromString(idClienteStr);

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
    @PermitAll
    public Response buscarClientes() {
        try {
            LOG.info("Requisição recebida - Buscar o Cliente");
            List<BuscarClienteDTO> clienteDTO = clienteService.buscarClientes();
            return Response.status(200).entity(clienteDTO).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Deletando o cadastro do cliente logado")
    @APIResponse(responseCode = "200", description = "Cadastro deletado com sucesso!")
    @APIResponse(responseCode = "404", description = "O cliente não foi encontrado")
    @DELETE
    @Transactional
    @RolesAllowed("CLIENTE")
    @Path("/deletar")
    public Response deletarCliente(@Context JsonWebToken jwt) throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Deletar o cadastro do Cliente logado");

            UUID idCliente = UUID.fromString(jwt.getSubject());
            clienteService.deletarCliente(idCliente);

            return Response.status(200).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Atualizando o cadastro do cliente logado")
    @APIResponse(responseCode = "200", description = "Cadastro atualizado com sucesso!")
    @APIResponse(responseCode = "404", description = "O cliente não foi encontrado")
    @PUT
    @Transactional
    @Path("/atualizar")
    @RolesAllowed("CLIENTE")
    public Response atualizarCliente(@Context JsonWebToken jwt, @RequestBody AtualizarClienteDTO clienteDTO)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Atualizar o cadastro do Cliente logado");

            clienteDTO.setIdCliente(UUID.fromString(jwt.getSubject()));
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
    @GET
    @PermitAll
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

    @Operation(summary = "Alterar apenas a senha do cliente logado")
    @APIResponse(responseCode = "200", description = "Senha alterada com sucesso!")
    @APIResponse(responseCode = "404", description = "Cliente não encontrado")
    @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    @PUT
    @Path("/alterar-senha")
    @RolesAllowed("CLIENTE")
    @Transactional
    public Response alterarSenha(@Context JsonWebToken jwt, @Valid AlterarSenhaClienteDTO dto) {
        try {
            LOG.info("Requisição recebida - Alterar senha do Cliente");

            UUID idCliente = UUID.fromString(jwt.getSubject());
            clienteService.alterarSenha(idCliente, dto.getNovaSenha());

            return Response.noContent().build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

}
