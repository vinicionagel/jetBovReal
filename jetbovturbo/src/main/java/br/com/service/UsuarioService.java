package br.com.service;

import br.com.constantes.ConstPermissoes;
import br.com.exception.UsuarioFoundException;
import br.com.model.Usuario;
import br.com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/service")
@CrossOrigin(origins="http://localhost:4200")
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Secured({ConstPermissoes.ROLE_USUARIO_INSERIR})
	@RequestMapping(value="/usuario", method = RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Usuario> salvar(@RequestBody @Valid Usuario usuario) {
		verificarUsuarioExiste(usuario);
		this.usuarioRepository.save(usuario);
		return new ResponseEntity<>(usuario, HttpStatus.CREATED);
	}
	
	private void verificarUsuarioExiste(Usuario usuario) throws UsuarioFoundException {
		String email = usuario.getEmail();
		Usuario usuarioBuscado = usuarioRepository.findByEmail(email);
		if (usuarioBuscado != null) {
			throw new UsuarioFoundException("Usuario ja cadastrado");
		}
	}

	@RequestMapping(value="/usuario", method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({ConstPermissoes.ROLE_USUARIO_INSERIR})
	public ResponseEntity<Usuario> atualizar(@RequestBody @Valid Usuario usuario) {
		this.usuarioRepository.save(usuario);
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}
	
	@RequestMapping(value="/usuario", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({ConstPermissoes.ROLE_USUARIO_PESQUISAR})
	public @ResponseBody List<Usuario> consultar() {
		return this.usuarioRepository.findAll();
	}

	@Secured({ConstPermissoes.ROLE_USUARIO_PESQUISAR})
	@RequestMapping(value="/usuario/{email}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Usuario buscar(@PathVariable("email") String email) {
		return this.usuarioRepository.findByEmail(email);
	}
	
	@RequestMapping(value="/usuario/{codigo}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({ConstPermissoes.ROLE_USUARIO_INSERIR})
	public void excluir(@PathVariable("codigo") Long codigo){
		usuarioRepository.deleteById(codigo);
	}

}
