package salao.online.application.resources;

import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
import salao.online.application.dtos.ImagemDTO;
import salao.online.application.services.interfaces.ImagemService;
import salao.online.domain.formulario.ImagemUploadForm;

@Path("/imagem")
@Tag(name = "Endpoints do Cloudinary - Imagem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ImagemResource {

    @Inject
    ImagemService imagemService;

    @Operation(summary = "Cadastrando uma Imagem")
    @APIResponse(responseCode = "201", description = "Imagem cadastrada com sucesso!")
    @APIResponse(responseCode = "500", description = "Ocorreu um erro na requisição.")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response salvarImagem(ImagemUploadForm form) {
        try {
            ImagemDTO imagemDTO = imagemService.salvarImagem(form.getImageBytes(), form.getNomeArquivo());
            return Response.status(Response.Status.OK).entity(imagemDTO).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Ocorreu um erro ao salvar a imagem.").build();
        }
    }

    @Operation(summary = "Atualizando uma Imagem")
    @APIResponse(responseCode = "200", description = "Imagem atualizada com sucesso!")
    @APIResponse(responseCode = "404", description = "Imagem não encontrada.")
    @APIResponse(responseCode = "500", description = "Ocorreu um erro na requisição.")
    @PUT
    @Path("atualizar/{id}")
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response atualizarImagem(@PathParam("id") UUID id, ImagemUploadForm form) {
        try {
            var imagemAtualizada = imagemService.atualizarImagem(id, form.getImageBytes(), form.getNomeArquivo());
            return imagemAtualizada.map(img -> Response.ok("Imagem atualizada com sucesso!").build())
                    .orElse(Response.status(Response.Status.NOT_FOUND)
                            .entity("Imagem não encontrada.").build());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Ocorreu um erro ao atualizar a imagem.").build();
        }
    }

    @Operation(summary = "Buscando uma Imagem")
    @APIResponse(responseCode = "200", description = "Imagem encontrada.")
    @APIResponse(responseCode = "404", description = "Imagem não encontrada.")
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarImagemPorId(@PathParam("id") UUID id) {
        var imagemDTO = imagemService.buscarImagemPorId(id);
        return imagemDTO.map(img -> Response.ok(img).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("Imagem não encontrada.").build());
    }

    @Operation(summary = "Deletando uma Imagem")
    @APIResponse(responseCode = "204", description = "Imagem deletada com sucesso.")
    @APIResponse(responseCode = "404", description = "Imagem não encontrada.")
    @DELETE
    @Path("/deletar/{id}")
    public Response deletarImagem(@PathParam("id") UUID id) {
        boolean deletado = imagemService.deletarImagem(id);
        if (deletado) {
            return Response.status(Response.Status.OK).entity("Imagem deletada com sucesso!").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Imagem não encontrada.").build();
        }
    }
}