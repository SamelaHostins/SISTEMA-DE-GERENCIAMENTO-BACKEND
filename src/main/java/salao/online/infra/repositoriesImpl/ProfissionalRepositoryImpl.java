package salao.online.infra.repositoriesImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import salao.online.domain.entities.Estoque;
import salao.online.domain.entities.Profissional;
import salao.online.domain.entities.Servico;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.enums.TipoImagemEnum;
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

    @Override
    public Optional<Profissional> buscarPeloEmail(String email) {
        // evita que letras maiusculas ou minusculas atrapalhem na busca e trata espaços
        // extras
        return find("LOWER(email)", email.trim().toLowerCase()).firstResultOptional();
    }

    /**
     * Retorna até `limit` profissionais que tenham imagem de perfil (tipo PERFIL).
     */
    @Override
    public List<Profissional> listarComImagemDePerfil(int limit) {
        PanacheQuery<Profissional> query = find(
                "select distinct p " +
                        "from Profissional p " +
                        "  join p.imagens img " +
                        "where img.tipoImagem = :tipo",
                Parameters.with("tipo", TipoImagemEnum.PERFIL));
        // Página zero (primeira página) com tamanho = limit
        return query.page(Page.of(0, limit)).list();
    }

    /**
     * Retorna profissionais que têm imagem de perfil e pelo menos um serviço
     * cadastrado.
     */
    @Override
    public List<Profissional> buscarProfissionaisComImagemEComServico() {
        return find(
                "select distinct p from Profissional p " +
                        "join p.imagens img " +
                        "where img.tipoImagem = :tipo " +
                        "and exists (" +
                        "  select 1 from Servico s " +
                        "  where s.profissional.idProfissional = p.idProfissional" +
                        ")",
                Parameters.with("tipo", TipoImagemEnum.PERFIL)).list();
    }
}
