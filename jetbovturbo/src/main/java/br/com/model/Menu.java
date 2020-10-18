package br.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "interface")
public class Menu {

    private static final long serialVersionUID = 12L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    protected Long codigo;

    @Column(name = "descricao_completa")
    private String descricaoCompleta;
    @Column(name = "descricao_curta")
    private String descricaoCurta;
    @Column(name = "posicao_menu")
    private int posicaoMenu;
    private String url;
    private String icone;
    private String cor;

    @ManyToOne()
    @JoinColumn(name = "interface_codigo")
    private Menu interfaceRecursiva;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricaoCompleta() {
        return descricaoCompleta;
    }

    public void setDescricaoCompleta(String descricaoCompleta) {
        this.descricaoCompleta = descricaoCompleta;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public int getPosicaoMenu() {
        return posicaoMenu;
    }

    public void setPosicaoMenu(int posicaoMenu) {
        this.posicaoMenu = posicaoMenu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Menu getInterfaceRecursiva() {
        return interfaceRecursiva;
    }

    public void setInterfaceRecursiva(Menu interfaceRecursiva) {
        this.interfaceRecursiva = interfaceRecursiva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(codigo, menu.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
