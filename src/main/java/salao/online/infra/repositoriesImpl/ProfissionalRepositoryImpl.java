package salao.online.infra.repositoriesImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import salao.online.domain.entities.Profissional;
import salao.online.domain.entities.Servico;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.ProfissionalRepository;

@ApplicationScoped
public class ProfissionalRepositoryImpl implements ProfissionalRepository {

    @Override
    public Optional<Profissional> deletarCadastroDoProfissional(UUID idProfissional){
        Optional<Profissional> profissionalOptional = findByIdOptional(idProfissional);
        if (profissionalOptional.isPresent()) {
            deleteById(idProfissional);
        }
        return profissionalOptional;
    }

    @Override
    public List<Servico> buscarServicosDoProfissional(UUID idProfissional) throws ValidacaoException {
        return findByIdOptional(idProfissional)
                .orElseThrow(
                        () -> new ValidacaoException(MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()))
                .getServicos().stream()
                .collect(Collectors.toList());
    }

}
