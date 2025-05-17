package salao.online.infra.repositoriesImpl;

import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import salao.online.domain.entities.HorarioTrabalho;
import salao.online.infra.repositories.HorarioTrabalhoRepository;

@ApplicationScoped
public class HorarioTrabalhoRepositoryImpl implements HorarioTrabalhoRepository {
    
    public List<HorarioTrabalho> listarPorProfissional(UUID idProfissional) {
        return find("idProfissional", idProfissional).list();
    }

}
