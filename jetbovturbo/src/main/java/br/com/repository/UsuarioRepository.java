package br.com.repository;

import br.com.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @SuppressWarnings("unchecked")
    Usuario save(Usuario usuario);

    void delete(Usuario usuario);

    List<Usuario> findAll();

    Usuario findByEmail(String email);

    @Query("FROM Usuario WHERE nome = ?1 or situacaoUsuario = ?2 or perfilAcessoUsuario = ?3 ")
    List<Usuario> findByNomeSituacaoPerfil(String nome);


}
