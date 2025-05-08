package salao.online.application.resources;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestForm;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import salao.online.application.dtos.dtosDeImagem.ImagemDTO;
import salao.online.application.services.interfaces.ImagemService;

@Path("/imagem")
@Tag(name = "Endpoints do Cloudinary - Imagem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ImagemResource {

    @Inject
    ImagemService imagemService;

    @Inject
    JsonWebToken jwt;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ImagemResource.class);

    @POST
    @Transactional
    @Path("/upload/{tipoImagem}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({ "CLIENTE", "PROFISSIONAL" })
    public Response uploadImagem(
            @RestForm("imageBytes") InputStream imageBytes,
            @PathParam("tipoImagem") int tipoImagem,
            @Context SecurityContext securityContext) {

        try {
            if (imageBytes == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Parâmetro inválido: forneça a imagem.")
                        .build();
            }

            UUID idUsuario = UUID.fromString(securityContext.getUserPrincipal().getName());
            boolean ehProfissional = jwt.getGroups().contains("PROFISSIONAL");

            String url = imagemService.uploadImagem(imageBytes, tipoImagem, idUsuario, ehProfissional);

            if (url != null) {
                LOG.info("Upload realizado com sucesso. URL: " + url);
                return Response.ok(Map.of("url", url)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Falha ao realizar o upload da imagem.")
                        .build();
            }
        } catch (Exception e) {
            LOG.error("Erro ao fazer upload da imagem", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao realizar o upload da imagem.")
                    .build();
        }
    }

    @DELETE
    @Transactional
    @Path("/excluir/{idImagem}")
    @RolesAllowed({ "CLIENTE", "PROFISSIONAL" })
    public Response excluirImagem(@PathParam("idImagem") UUID idImagem) {
        try {
            imagemService.excluirImagem(idImagem);
            return Response.ok("Imagem excluída com sucesso.").build();
        } catch (IllegalArgumentException e) {
            return Response.status(404).entity(e.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Erro ao excluir imagem.").build();
        }
    }

    @GET
    @Path("/perfil")
    @PermitAll
    public Response buscarImagemDePerfil(@Context SecurityContext securityContext) {
        UUID idUsuario = UUID.fromString(securityContext.getUserPrincipal().getName());
        ImagemDTO imagem = imagemService.buscarImagemDePerfil(idUsuario);

        if (imagem == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.ok(imagem).build();
    }

    @GET
    @Path("/portfolio")
    @PermitAll
    public Response buscarFotosDoPortfolio(@Context SecurityContext securityContext) {
        UUID idUsuario = UUID.fromString(securityContext.getUserPrincipal().getName());
        List<ImagemDTO> imagens = imagemService.buscarFotosDoPortfolio(idUsuario);
        return Response.ok(imagens).build();
    }
}
