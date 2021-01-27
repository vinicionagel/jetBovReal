package br.com.service;

import br.com.constantes.ConstPermissoes;
import br.com.core.ModelMapperUsuarioConfig;
import br.com.dto.UsuarioDTO;
import br.com.exception.UsuarioFoundException;
import br.com.model.Usuario;
import br.com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapperUsuarioConfig modelMapperUsuarioConfig;

    @RequestMapping(value = "/usuario", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Secured({ConstPermissoes.ROLE_USUARIO_INSERIR})
    public @ResponseBody
    ResponseEntity<Usuario> salvar(@RequestBody @Valid Usuario usuario) {
        verificarUsuarioExiste(usuario);
        this.usuarioRepository.save(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @Secured({ConstPermissoes.ROLE_USUARIO_INSERIR})
    @RequestMapping(value = "/usuario", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Usuario> atualizar(@RequestBody @Valid Usuario usuario) {
        this.usuarioRepository.save(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @Secured({ConstPermissoes.ROLE_USUARIO_PESQUISAR})
    @RequestMapping(value = "/usuario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    List<UsuarioDTO> consultar() {
        return this.usuarioRepository.findAll().stream()
                .map(usuario -> modelMapperUsuarioConfig.modelMapper()
                        .map(usuario, UsuarioDTO.class)).collect(Collectors.toList());
    }

    @Secured({ConstPermissoes.ROLE_USUARIO_PESQUISAR})
    @RequestMapping(value = "/usuario/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    UsuarioDTO buscar(@PathVariable("email") String email) {
        return modelMapperUsuarioConfig.modelMapper().map(this.usuarioRepository.findByEmail(email), UsuarioDTO.class);
    }

    @RequestMapping(value = "/usuario/{codigo}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void excluir(@PathVariable("codigo") Long codigo) {
        usuarioRepository.deleteById(codigo);
    }

    private void verificarUsuarioExiste(Usuario usuario) {
        String email = usuario.getEmail();
        Usuario usuarioBuscado = usuarioRepository.findByEmail(email);
        if (usuarioBuscado != null) {
            throw new UsuarioFoundException("Usuario ja cadastrado");
        }
    }

    //https://www.youtube.com/watch?v=HU7bfKG8nV4 estudando...

}
