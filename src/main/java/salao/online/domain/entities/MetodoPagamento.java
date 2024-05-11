package salao.online.domain.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;

@Entity
@Table(schema = "salao", name = "metodo_pagamento")
public class MetodoPagamento {

    protected MetodoPagamento(){}

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_metodo_pagamento")
    @NotNull
    private @Getter UUID idMetodoPagamento;

    @NotEmpty
    @Size(max = 20, message = "O nome deve ter no máximo 20 caracteres")
    private @Getter String nome;

    public MetodoPagamento(String nome){
        this.nome = nome;
    }
    
}
