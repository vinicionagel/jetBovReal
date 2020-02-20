package br.com.exception;

public class UsuarioFoundException extends RuntimeException {

	private static final long serialVersionUID = -6011969902104993547L;

	public UsuarioFoundException(String mensagem) {
		super(mensagem);
	}
	
}
