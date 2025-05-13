package salao.online.domain.entities;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "estoque", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_profissional" }) })
public class Estoque {

    protected Estoque() {
    }

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
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
    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private @Getter List<Produto> produtos = new ArrayList<>();

    public Estoque(String nome, int qtdDeProdutos, List<Produto> produtos, Profissional profissional) {
        this.nome = nome;
        this.qtdDeProdutos = 0;
        this.produtos = produtos;
        this.profissional = profissional;
    }

}
