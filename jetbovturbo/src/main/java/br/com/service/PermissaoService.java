package br.com.service;

import br.com.constantes.ConstPermissoes;
import br.com.model.Permissao;
import br.com.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permissao")
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Secured({ConstPermissoes.ROLE_PERMISSAO_PESQUISAR})
    @RequestMapping(value="/{codigoUsuario}", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<Permissao> buscar(@PathVariable("codigoUsuario") Long codigoUsuario) {
        return this.permissaoRepository.permissoesUsuario(codigoUsuario);
    }

}
