package salao.online.infra.repositoriesImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import salao.online.domain.entities.Estoque;
import salao.online.domain.entities.Profissional;
import salao.online.domain.entities.Servico;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.enums.TipoServicoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.ProfissionalRepository;

@ApplicationScoped
public class ProfissionalRepositoryImpl implements ProfissionalRepository {

    @Override
    public Optional<Profissional> deletarProfissional(UUID idProfissional) {
        Optional<Profissional> profissionalOptional = findByIdOptional(idProfissional);
        if (profissionalOptional.isPresent()) {
            deleteById(idProfissional);
        }
        return profissionalOptional;
    }

    @Override
    public List<Servico> buscarServicosDoProfissional(UUID idProfissional, TipoServicoEnum tipoServico)
            throws ValidacaoException {
        Profissional profissional = findByIdOptional(idProfissional)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));
        // Define o tipo de serviço padrão como NORMAL se não especificado
        final TipoServicoEnum tipoServicoFinal = (tipoServico == null) ? TipoServicoEnum.NORMAL : tipoServico;
        return profissional.getServicos().stream()
                .filter(servico -> servico.getTipoServico() == tipoServicoFinal)
                .collect(Collectors.toList());
    }

    @Override
    public List<Servico> buscarTodosOsServicosDoProfissional(UUID idProfissional) throws ValidacaoException {
        Profissional profissional = findByIdOptional(idProfissional)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));
        return profissional.getServicos().stream().collect(Collectors.toList());
    }

    
    @Override
    public List<Estoque> buscarEstoquesDoProfissional(UUID idProfissional) throws ValidacaoException {
        Profissional profissional = findByIdOptional(idProfissional)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));
        return profissional.getEstoques().stream().collect(Collectors.toList());
    }
}
