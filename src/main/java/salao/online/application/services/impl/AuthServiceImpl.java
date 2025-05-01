package salao.online.application.services.impl;

import java.time.Duration;
import java.util.Optional;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import salao.online.application.services.interfaces.AuthService;
import salao.online.domain.entities.Cliente;
import salao.online.domain.entities.Profissional;
import salao.online.domain.enums.TipoUsuarioEnum;
import salao.online.infra.repositories.ClienteRepository;
import salao.online.infra.repositories.ProfissionalRepository;

@ApplicationScoped
public class AuthServiceImpl implements AuthService{

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ProfissionalRepository profissionalRepository;

    public Optional<ResultadoLogin> autenticar(String email, String senha) {
        Optional<Cliente> cliente = clienteRepository.buscarPeloEmail(email);
        if (cliente.isPresent() && cliente.get().getSenha().equals(senha)) {
            return Optional.of(new ResultadoLogin(gerarToken(email, TipoUsuarioEnum.CLIENTE), TipoUsuarioEnum.CLIENTE));
        }

        Optional<Profissional> profissional = profissionalRepository.buscarPeloEmail(email);
        if (profissional.isPresent() && profissional.get().getSenha().equals(senha)) {
            return Optional.of(new ResultadoLogin(gerarToken(email, TipoUsuarioEnum.PROFISSIONAL), TipoUsuarioEnum.PROFISSIONAL));
        }

        return Optional.empty();
    }

    private String gerarToken(String email, TipoUsuarioEnum tipoUsuario) {
        return Jwt.issuer("salao.online")
                .subject(email)
                .claim("tipoUsuario", tipoUsuario.name())
                .expiresIn(Duration.ofHours(3))
                .sign();
    }

    // Classe auxiliar interna, só para encapsular retorno da autenticação
    public static class ResultadoLogin {
        public String token;
        public TipoUsuarioEnum tipoUsuario;

        public ResultadoLogin(String token, TipoUsuarioEnum tipoUsuario) {
            this.token = token;
            this.tipoUsuario = tipoUsuario;
        }
    }
}
