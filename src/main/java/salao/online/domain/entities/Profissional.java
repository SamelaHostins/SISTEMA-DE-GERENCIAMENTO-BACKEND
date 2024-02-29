package salao.online.domain.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "profissional", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_endereco" }) })
public class Profissional extends Informacao {

    protected Profissional() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_profissional")
    @NotNull
    private @Getter UUID idProfissional;

    @NotNull
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    private @Getter Endereco endereco;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissional")
    private @Getter List<Servico> servicos;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissional")
    private @Getter List<Estoque> estoques;

    public Profissional(Informacao informacao, Endereco endereco, List<Servico> servicos, List<Estoque> estoques) {
        this.endereco = endereco;
        this.servicos = servicos;
        this.estoques = estoques;
    }

}
