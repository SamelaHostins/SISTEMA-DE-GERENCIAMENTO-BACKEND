package salao.online.domain.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "salao", name = "endereco")
public class Endereco {

    protected Endereco() {
    }

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "id_endereco")
    @NotNull
    private @Setter @Getter UUID idEndereco;

    @NotNull
    private @Setter @Getter String nome;

    @NotEmpty
    private @Setter @Getter String rua;

    @NotEmpty
    private @Setter @Getter String bairro;

    @NotEmpty
    private @Setter @Getter String cidade;

    @NotEmpty
    private @Setter @Getter String estado;

    @NotEmpty
    private @Setter @Getter int numero;

    @NotNull
    private @Setter @Getter String cep;

    private @Setter @Getter String complemento;

    public Endereco(String nome, String rua, String bairro, String cidade, int numero, String cep, String complemento) {
        this.nome = nome;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        this.cep = cep;
        this.complemento = complemento;
    }
}
