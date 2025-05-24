package salao.online.infra.repositoriesImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import salao.online.domain.entities.Avaliacao;
import salao.online.domain.entities.Servico;
import salao.online.domain.enums.TipoImagemEnum;
import salao.online.infra.repositories.ServicoRepository;

@ApplicationScoped
public class ServicoRepositoryImpl implements ServicoRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Avaliacao> buscarAvaliacoesDoServico(UUID idServico) {
        Servico servico = findByIdOptional(idServico).orElse(null);
        if (servico != null) {
            return servico.getAvaliacoes().stream()
                    .sorted(Comparator.comparing(Avaliacao::getDataAvaliacao).reversed())
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Servico> deletarServico(UUID idServico) {
        Optional<Servico> servicoOptional = findByIdOptional(idServico);
        if (servicoOptional.isPresent()) {
            deleteById(idServico);
        }
        return servicoOptional;
    }

    /**
     * Retorna serviços de profissionais que têm imagem de perfil
     * e pelo menos um serviço cadastrado.
     */
    @Override
    public List<Servico> buscarServicosDeProfissionaisComImagemEComServico() {
        String jpql = """
            SELECT s FROM Servico s
            WHERE s.profissional.idProfissional IN (
                SELECT p.idProfissional FROM Profissional p
                JOIN p.imagens img
                WHERE img.tipoImagem = :tipo
                AND EXISTS (
                    SELECT 1 FROM Servico s2
                    WHERE s2.profissional.idProfissional = p.idProfissional
                )
            )
        """;

        TypedQuery<Servico> query = em.createQuery(jpql, Servico.class);
        query.setParameter("tipo", TipoImagemEnum.PERFIL);
        return query.getResultList();
    }
}
