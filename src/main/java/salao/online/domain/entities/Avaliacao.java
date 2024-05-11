package salao.online.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "avaliacao", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_cliente", "id_servico" }) })
public class Avaliacao {

    protected Avaliacao() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_avaliacao")
    @NotNull
    private @Getter UUID idAvaliacao;

    @NotNull
    @PositiveOrZero
    private @Getter int nota;

    @CreationTimestamp
    @Column(name = "dt_avaliacao")
    private @Getter LocalDate dataAvaliacao;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private @Getter Cliente cliente;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servico")
    private @Getter Servico servico;

    public Avaliacao(int nota, Cliente cliente, Servico servico) {
        this.nota = nota;
        this.cliente = cliente;
        this.servico = servico;
    }

    public Avaliacao atualizarAvaliacao(int nota) {
        this.nota = nota;
        return this;
    }

}