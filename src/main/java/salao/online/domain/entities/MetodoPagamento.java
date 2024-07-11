package salao.online.domain.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "metodo_pagamento")
public class MetodoPagamento {

    protected MetodoPagamento() {
    }

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "id_metodo_pagamento")
    @NotNull
    private @Getter UUID idMetodoPagamento;

    @NotBlank
    @Size(max = 20, message = "O nome deve ter no máximo 20 caracteres")
    private @Getter String nome;

    public MetodoPagamento(String nome) {
        this.nome = nome;
    }

}
