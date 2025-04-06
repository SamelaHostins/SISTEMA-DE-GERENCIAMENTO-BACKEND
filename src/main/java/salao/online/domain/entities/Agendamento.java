package salao.online.domain.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;
import salao.online.domain.enums.FormaPagamentoEnum;
import salao.online.domain.enums.StatusAgendamentoEnum;

@Entity
@Table(schema = "salao", name = "agendamento")
public class Agendamento {

    protected Agendamento() {
    }

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "id_agendamento")
    @NotNull
    private @Getter UUID idAgendamento;

    @NotNull
    @FutureOrPresent(message = "A data de agendamento deve ser hoje ou no futuro")
    @Column(name = "dt_agendamento")
    private @Getter LocalDate dataAgendamento;

    @NotNull
    @Column(name = "hora_agendamento")
    private @Getter LocalTime horaAgendamento;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "status_agendamento")
    private @Getter @Setter StatusAgendamentoEnum statusAgendamento;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "forma_pagamento")
    private @Getter @Setter FormaPagamentoEnum formaPagamento;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private @Getter @Setter Cliente cliente;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servico")
    private @Getter @Setter Servico servico;

    public Agendamento(LocalDate dataAgendamento, LocalTime horaAgendamento,
            StatusAgendamentoEnum statusAgendamento, FormaPagamentoEnum formaPagamento,
            Cliente cliente, Servico servico) {
        this.dataAgendamento = dataAgendamento;
        this.horaAgendamento = horaAgendamento;
        this.statusAgendamento = statusAgendamento;
        this.formaPagamento = formaPagamento;
        this.cliente = cliente;
        this.servico = servico;
    }

}
