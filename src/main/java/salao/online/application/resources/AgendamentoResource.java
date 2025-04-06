package salao.online.application.resources;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.application.dtos.dtosDoAgendamento.FormaPagamentoEnumDTO;
import salao.online.application.dtos.dtosDoAgendamento.StatusAgendamentoEnumDTO;
import salao.online.application.services.interfaces.AgendamentoService;

@Path("/agendamentos")
@Produces(MediaType.APPLICATION_JSON)
public class AgendamentoResource {

    @Inject
    AgendamentoService agendamentoService;

    @GET
    @Path("/profissional/{idProfissional}")
    public List<AgendamentoDTO> buscarAgendamentosDoProfissional(
        @PathParam("idProfissional") UUID idProfissional,
        @QueryParam("data") LocalDate data,
        @QueryParam("status") StatusAgendamentoEnumDTO status,
        @QueryParam("formaPagamento") FormaPagamentoEnumDTO formaPagamento
    ) {
        return agendamentoService.buscarAgendamentosDoProfissional(idProfissional, data, status, formaPagamento);
    }

    @GET
    @Path("/cliente/{idCliente}")
    public List<AgendamentoDTO> buscarAgendamentosDoCliente(
        @PathParam("idCliente") UUID idCliente,
        @QueryParam("data") LocalDate data,
        @QueryParam("status") StatusAgendamentoEnumDTO status,
        @QueryParam("formaPagamento") FormaPagamentoEnumDTO formaPagamento
    ) {
        return agendamentoService.buscarAgendamentosDoCliente(idCliente, data, status, formaPagamento);
    }
}
