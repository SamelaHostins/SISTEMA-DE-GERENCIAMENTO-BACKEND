package salao.online.domain.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;
import lombok.Setter;
import salao.online.domain.enums.TipoServicoEnum;

@Entity
@Table(schema = "salao", name = "servico", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_profissional" }) })
public class Servico {

    protected Servico() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_servico")
    @NotNull
    private @Getter UUID idServico;

    @Column(name = "tipo_servico")
    private @Getter @Setter TipoServicoEnum tipoServico;

    @NotBlank
    private @Getter @Setter String nome;

    private @Getter @Setter String especificacao;

    @Column(name = "termos_e_condicoes")
    private @Getter @Setter String termosECondicoes;

    @DecimalMin(value = "0.01", message = "O valor deve ser no mínimo 0.01")
    private @Getter @Setter double valor;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profissional")
    private @Getter Profissional profissional;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servico")
    private @Getter List<Avaliacao> avaliacoes;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servico")
    private @Getter List<Agendamento> agendamentos;

    public Servico(TipoServicoEnum tipoServico, String nome, String especificacao, String termosECondicoes,
            double valor,
            Profissional profissional, List<Avaliacao> avaliacoes, List<Agendamento> agendamentos) {
        this.tipoServico = tipoServico;
        this.nome = nome;
        this.especificacao = especificacao;
        this.termosECondicoes = termosECondicoes;
        this.valor = valor;
        this.profissional = profissional;
        this.avaliacoes = avaliacoes;
        this.agendamentos = agendamentos;
    }

    public Servico atualizarCadastroServico(TipoServicoEnum novoTipoServico, String novoNome, String novaEspecificacao,
            String novosTermosECondicoes, double novoValor) {
        setTipoServico(novoTipoServico);
        setNome(novoNome);
        setEspecificacao(novaEspecificacao);
        setTermosECondicoes(novosTermosECondicoes);
        setValor(novoValor);
        return this;
    }

}
