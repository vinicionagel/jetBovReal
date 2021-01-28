package br.com.exceptionHandler;

import br.com.dto.ErroDTO;
import br.com.exception.UsuarioFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class JetBovExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public JetBovExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String mensagemDesenvolver = ex.getCause().toString();
        String mensagemUsuario = getMessgeSource("mensagem.invalida");
        List<ErroDTO> erros = Collections.singletonList(new ErroDTO(mensagemUsuario, mensagemDesenvolver));
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException emptyResultException, WebRequest request) {
        String mensagemDesenvolver = ExceptionUtils.getRootCauseMessage(emptyResultException);
        String mensagemUsuario = getMessgeSource("mensagem.RegistroNaoEncontrado");
        List<ErroDTO> erros = Collections.singletonList(new ErroDTO(mensagemUsuario, mensagemDesenvolver));
        return handleExceptionInternal(emptyResultException, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handlePSQLExceptionException(ConstraintViolationException constraintViolation, WebRequest request) {
        String mensagemDesenvolver = ExceptionUtils.getRootCauseMessage(constraintViolation);
        String mensagemUsuario = getMessgeSource("mensagem.chaveEstrangeira");
        List<ErroDTO> erros = Collections.singletonList(new ErroDTO(mensagemUsuario, mensagemDesenvolver));
        return handleExceptionInternal(constraintViolation, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handlePSQLExceptionException(EntityNotFoundException entityNotFoundException, WebRequest request) {
        String mensagemDesenvolver = ExceptionUtils.getRootCauseMessage(entityNotFoundException);
        String mensagemUsuario = getMessgeSource("mensagem.chaveEstrangeira");
        List<ErroDTO> erros = Collections.singletonList(new ErroDTO(mensagemUsuario, mensagemDesenvolver));
        return handleExceptionInternal(entityNotFoundException, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({UsuarioFoundException.class})
    public ResponseEntity<Object> handlePSQLExceptionException(UsuarioFoundException UsuarioNotFoundException, WebRequest request) {
        String mensagemDesenvolver = ExceptionUtils.getRootCauseMessage(UsuarioNotFoundException);
        String mensagemUsuario = getMessgeSource("mensagem.registroJaGravado");
        List<ErroDTO> erros = Collections.singletonList(new ErroDTO(mensagemUsuario, mensagemDesenvolver));
        return handleExceptionInternal(UsuarioNotFoundException, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErroDTO> erros = criarListaErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<ErroDTO> criarListaErros(BindingResult bindingResult) {
        List<ErroDTO> erros = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String mensagemDesenvolvedor = fieldError.toString();
            String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            erros.add(new ErroDTO(mensagemUsuario, mensagemDesenvolvedor));
        }
        return erros;
    }

    private String getMessgeSource(String mensagem) {
        return messageSource.getMessage(mensagem, null, LocaleContextHolder.getLocale());
    }

}
