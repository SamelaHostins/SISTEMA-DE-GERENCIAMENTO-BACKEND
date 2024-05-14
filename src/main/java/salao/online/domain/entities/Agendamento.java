package salao.online.domain.entities;

import java.sql.Time;
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
import javax.validation.constraints.FutureOrPresent;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;
import salao.online.domain.enums.StatusAgendamentoEnum;

@Entity
@Table(schema = "salao", name = "agendamento", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_cliente", "id_servico" }) })
public class Agendamento {

    protected Agendamento() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_agendamento")
    @NotNull
    private @Getter UUID idAgendamento;

    @NotNull
    @FutureOrPresent(message = "A data de agendamento deve ser hoje ou no futuro")
    @Column(name = "dt_agendamento")
    private @Getter LocalDate dataAgendamento;

    @NotNull
    @Column(name = "hora_agendamento")
    private @Getter Time horaAgendamento;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "status_agendamento")
    private @Getter StatusAgendamentoEnum statusAgendamento;

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

    public Agendamento(LocalDate dataAgendamento, Time horaAgendamento,
            StatusAgendamentoEnum statusAgendamento, Cliente cliente, Servico servico) {
        this.dataAgendamento = dataAgendamento;
        this.horaAgendamento = horaAgendamento;
        this.statusAgendamento = statusAgendamento;
        this.cliente = cliente;
        this.servico = servico;
    }
}
