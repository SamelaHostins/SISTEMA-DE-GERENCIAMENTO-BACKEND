package salao.online.infra.repositoriesImpl;

import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import salao.online.domain.entities.Profissional;
import salao.online.infra.repositories.ProfissionalRepository;

@ApplicationScoped
public class ProfissionalRepositoryImpl implements ProfissionalRepository {

    public Optional<Profissional> deletarCadastroDoProfissional(UUID idProfissional){
        Optional<Profissional> profissionalOptional = findByIdOptional(idProfissional);
        if (profissionalOptional.isPresent()) {
            deleteById(idProfissional);
        }
        return profissionalOptional;
    }

}
