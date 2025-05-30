package salao.online.domain.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import salao.online.domain.enums.TipoImagemEnum;

@Entity
@Setter
@Table(schema = "salao", name = "imagem")
public class Imagem {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "id_imagem")
    @NotNull
    private @Getter UUID idImagem;

    @NotNull
    @Column(name = "nome_arquivo")
    private @Getter @Setter String nomeArquivo;

    @NotNull
    @Column(name = "url_imagem")
    private @Getter @Setter String urlImagem;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_profissional", nullable = true)
    private @Getter @Setter Profissional profissional;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_cliente", nullable = true)
    private @Getter @Setter Cliente cliente;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_imagem", nullable = false)
    private @Getter @Setter TipoImagemEnum tipoImagem;

    public Imagem() {
    }

    public Imagem(String nomeArquivo, String urlImagem, TipoImagemEnum tipoImagem) {
        this.nomeArquivo = nomeArquivo;
        this.urlImagem = urlImagem;
        this.tipoImagem = tipoImagem;
    }

}