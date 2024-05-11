package salao.online.domain.entities;

import java.util.UUID;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "estoque", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "id_profissional"}) })
public class Estoque {

    protected Estoque() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_estoque")
    @NotNull
    private @Getter UUID idEstoque;

    @NotBlank
    @Size(min = 3, max = 55, message = "O nome deve ter entre 3 e 55 caracteres")
    private @Getter String nome;

    @Column(name = "qtd_de_produtos")
    private @Getter int qtdDeProdutos;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profissional")
    private @Getter Profissional profissional;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estoque")
    private @Getter List<Produto> produtos;

    public Estoque(String nome, int qtdDeProdutos, List<Produto> produtos, Profissional profissional) {
        this.nome = nome;
        this.qtdDeProdutos = qtdDeProdutos;
        this.produtos = produtos;
        this.profissional = profissional;
    }

}
