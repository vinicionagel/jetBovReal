package br.com.service;

import java.util.List;

import javax.validation.Valid;

import br.com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.exception.UsuarioFoundException;

@RestController
@RequestMapping("/service")
@CrossOrigin(origins="http://localhost:4200")
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping(value="/usuario", method = RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<UsuarioModel> salvar(@RequestBody @Valid UsuarioModel usuario) {
		verificarUsuarioExiste(usuario);
		this.usuarioRepository.save(usuario);
		return new ResponseEntity<>(usuario, HttpStatus.CREATED);
	}
	
	private void verificarUsuarioExiste(UsuarioModel usuario) throws UsuarioFoundException {
		String cpf = usuario.getCpf();
		UsuarioModel usuarioBuscado = usuarioRepository.findByCpf(cpf);
		if (usuarioBuscado != null) {
			throw new UsuarioFoundException("Usuario ja cadastrado");
		}
	}

	@RequestMapping(value="/usuario", method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UsuarioModel> atualizar(@RequestBody @Valid UsuarioModel usuario) {
		this.usuarioRepository.save(usuario);
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}
	
	@RequestMapping(value="/usuario", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody List<UsuarioModel> consultar() {
		return this.usuarioRepository.findAll();
	}
	
	@RequestMapping(value="/usuario/{cpf}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody UsuarioModel buscar(@PathVariable("cpf") String cpf) {
		return this.usuarioRepository.findByCpf(cpf);
	}
	
	@RequestMapping(value="/usuario/{cpf}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void excluir(@PathVariable("cpf") String cpf){
		usuarioRepository.deleteById(cpf);	
	}

}
