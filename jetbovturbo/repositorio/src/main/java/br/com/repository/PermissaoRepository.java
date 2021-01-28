package br.com.repository;

import br.com.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    @Query("SELECT DISTINCT u.permissoes FROM Usuario as u WHERE u.codigo = ?1")
    List<Permissao> permissoesUsuario(Long codigoUsuario);

}
