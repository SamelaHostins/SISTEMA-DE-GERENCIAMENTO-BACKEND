package salao.online.application.services.impl;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
public class AuthServiceImpl implements AuthService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ProfissionalRepository profissionalRepository;

    @Override
    public Optional<ResultadoLogin> autenticar(String email, String senha) {
        // tenta autenticar cliente
        Optional<Cliente> clienteOpt = clienteRepository.buscarPeloEmail(email);
        if (clienteOpt.isPresent() && clienteOpt.get().getSenha().equals(senha)) {
            Cliente c = clienteOpt.get();
            String token = gerarToken(c.getIdCliente(), c.getEmail(), TipoUsuarioEnum.CLIENTE);
            return Optional.of(new ResultadoLogin(token, TipoUsuarioEnum.CLIENTE));
        }

        // tenta autenticar profissional
        Optional<Profissional> profOpt = profissionalRepository.buscarPeloEmail(email);
        if (profOpt.isPresent() && profOpt.get().getSenha().equals(senha)) {
            Profissional p = profOpt.get();
            String token = gerarToken(p.getIdProfissional(), p.getEmail(), TipoUsuarioEnum.PROFISSIONAL);
            return Optional.of(new ResultadoLogin(token, TipoUsuarioEnum.PROFISSIONAL));
        }

        return Optional.empty();
    }

    /**
     * Gera um JWT onde:
     * - sub = UUID do usuário
     * - claim "email" = e-mail do usuário
     * - claim "tipoUsuario" = CLIENTE ou PROFISSIONAL
     */
    private String gerarToken(UUID id, String email, TipoUsuarioEnum tipoUsuario) {
        return Jwt.issuer("salao.online")
                .subject(id.toString())
                .claim("email", email)
                .claim("tipoUsuario", tipoUsuario.name())
                .groups(Set.of(tipoUsuario.name()))
                .expiresIn(Duration.ofHours(2))
                .sign();
    }

    // Classe interna para resultado de autenticação
    public static class ResultadoLogin {
        public String token;
        public TipoUsuarioEnum tipoUsuario;

        public ResultadoLogin(String token, TipoUsuarioEnum tipoUsuario) {
            this.token = token;
            this.tipoUsuario = tipoUsuario;
        }
    }
}
