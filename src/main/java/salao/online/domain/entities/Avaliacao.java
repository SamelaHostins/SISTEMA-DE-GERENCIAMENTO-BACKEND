package salao.online.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "avaliacao", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_cliente", "id_servico" }) })
public class Avaliacao {

    protected Avaliacao() {
    }

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
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