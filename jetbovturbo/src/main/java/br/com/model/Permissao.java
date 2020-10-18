package br.com.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Objects;

@Entity
@Table(name = "permissao")
public class Permissao implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "codigo")
    protected Long codigo;

    @Column(name = "descricao", length = 255)
    protected String descricao;

    @ManyToOne()
    @JoinColumn(name = "tipo_permissao_codigo")
    private TipoPermissao tipoPermissao;

    @ManyToOne()
    @JoinColumn(name = "interface_codigo",
            insertable = true, updatable = true)
    private Menu interfaceDTO;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public TipoPermissao getTipoPermissao() {
        return tipoPermissao;
    }

    public void setTipoPermissao(TipoPermissao tipoPermissao) {
        this.tipoPermissao = tipoPermissao;
    }

    public br.com.model.Menu getInterfaceDTO() {
        return interfaceDTO;
    }

    public void setInterfaceDTO(Menu interfaceDTO) {
        this.interfaceDTO = interfaceDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Permissao permissao = (Permissao) o;
        return Objects.equals(codigo, permissao.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String getAuthority() {
        return MessageFormat.format("{0}_{1}_{2}", "ROLE",interfaceDTO.getDescricaoCurta(), descricao).toUpperCase();
    }
}
