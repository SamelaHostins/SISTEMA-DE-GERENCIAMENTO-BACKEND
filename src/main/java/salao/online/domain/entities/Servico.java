package salao.online.domain.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

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
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "id_servico")
    @NotNull
    private @Getter UUID idServico;

    @Enumerated(EnumType.ORDINAL)
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
