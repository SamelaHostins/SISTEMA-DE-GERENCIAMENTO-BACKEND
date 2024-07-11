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
import jakarta.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "produto")
public class Produto {

    protected Produto() {
    }

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "id_produto")
    @NotNull
    private @Getter UUID idProduto;

    @NotEmpty
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

    public Produto(String nome, LocalDate dtValidadeProduto, double valor, Estoque estoque) {
        this.nome = nome;
        this.dtValidadeProduto = dtValidadeProduto;
        this.valor = valor;
        this.estoque = estoque;

    }

}
