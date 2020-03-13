package br.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.model.Usuario;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@SuppressWarnings("unchecked")
    Usuario save(Usuario usuario);

	void delete(Usuario usuario);

	List<Usuario> findAll();

	Usuario findByEmail(String cpf);

	@Query("FROM Usuario WHERE nome = ?1 or situacaoUsuario = ?2 or perfilAcessoUsuario = ?3 ")
	List<Usuario> findByNomeSituacaoPerfil(String nome);
	
}
