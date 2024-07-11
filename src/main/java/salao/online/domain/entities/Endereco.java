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

@Entity
@Table(schema = "salao", name = "endereco")
public class Endereco {

    protected Endereco() {
    }

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "id_endereco")
    @NotNull
    private @Getter UUID idEndereco;

    @NotEmpty
    private @Getter String rua;

    @NotEmpty
    private @Getter String bairro;

    @NotEmpty
    private @Getter String cidade;

    @NotEmpty
    private @Getter int numero;

    @NotNull
    private @Getter String cep;

    public Endereco(String rua, String bairro, String cidade, int numero, String cep) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        this.cep = cep;
    }
}
