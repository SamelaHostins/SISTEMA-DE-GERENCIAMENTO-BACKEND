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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "servico")
public class Servico {

    protected Servico() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_servico")
    @NotNull
    private @Getter UUID idServico;

    @NotNull
    @Size(min = 3, max = 55, message = "O nome deve ter entre 3 e 55 caracteres")
    private @Getter String nome;

    @Size(max = 500, message = "A especificacao deve ter no máximo 500 caracteres")
    private @Getter String especificacao;

    @Column(name = "termos_e_condicoes")
    @Size(max = 1000, message = "Os termos e condições devem ter no máximo 1000 caracteres")
    private @Getter String termosECondicoes;

    @DecimalMin(value = "0.01", message = "O valor deve ser no mínimo 0.01")
    private @Getter double valor;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profissional")
    private @Getter Profissional profissional;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private @Getter List<Avaliacao> avaliacoes;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servico")
    private @Getter List<Agendamento> agendamentos;

    public Servico(String nome, String especificacao, String termosECondicoes, double valor, 
    Profissional profissional, List<Avaliacao> avaliacoes, List<Agendamento> agendamentos) {
        this.nome = nome;
        this.especificacao = especificacao;
        this.termosECondicoes = termosECondicoes;
        this.valor = valor;
        this.profissional = profissional;
        this.avaliacoes = avaliacoes;
        this.agendamentos = agendamentos;
    }

}
