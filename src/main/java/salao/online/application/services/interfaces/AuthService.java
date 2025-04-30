package salao.online.application.services.interfaces;

import java.util.Optional;

import salao.online.application.services.impl.AuthServiceImpl.ResultadoLogin;

public interface AuthService {
    
    Optional<ResultadoLogin> autenticar(String usuario, String senha);
}
