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

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "estoque")
public class Estoque {

    protected Estoque() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_estoque")
    @NotNull
    private @Getter UUID idEstoque;

    @NotEmpty
    @Size(max = 25, message = "O nome deve ter no máximo 25 caracteres")
    private @Getter String nome;

    private @Getter int qtdDeProdutos;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estoque")
    private @Getter List<Produto> produtos;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profissional")
    private @Getter Profissional profissional;


    public Estoque(String nome, int qtdDeProdutos, List<Produto> produtos, Profissional profissional) {
        this.nome = nome;
        this.qtdDeProdutos = qtdDeProdutos;
        this.produtos = produtos;
        this.profissional = profissional;
    }

}
