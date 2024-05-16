package salao.online.infra.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import salao.online.domain.entities.Profissional;
import salao.online.domain.entities.Servico;
import salao.online.domain.exceptions.ValidacaoException;

public interface ProfissionalRepository extends PanacheRepositoryBase<Profissional, UUID> {

    public Optional<Profissional> deletarCadastroDoProfissional(UUID idProfissional);
    
    public List<Servico> buscarServicosDoProfissional(UUID idProfissional) throws ValidacaoException;
}
