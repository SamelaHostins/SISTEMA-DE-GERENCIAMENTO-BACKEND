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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "produto")
public class Produto {
    
    protected Produto(){}

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_produto")
    @NotNull
    private @Getter UUID idProduto;

    @NotEmpty
    @Size(min= 3, max = 55, message = "O nome deve ter entre 3 e 55 caracteres")
    private @Getter String nome;

    @CreationTimestamp
    @Column(name = "dt_entrada")
    private @Getter LocalDate dtEntradaProduto;

    @Column(name = "dt_validade")
    private @Getter LocalDate dtValidadeProduto;

    @NotEmpty
    private @Getter double valor;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estoque")
    private @Getter Estoque estoque;

    public Produto(String nome, LocalDate dtValidadeProduto, double valor, Estoque estoque){
        this.nome = nome;
        this.dtValidadeProduto = dtValidadeProduto;
        this.valor = valor;
        this.estoque = estoque;

    }

}
