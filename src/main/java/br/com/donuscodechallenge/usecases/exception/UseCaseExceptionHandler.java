package br.com.donuscodechallenge.usecases.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class UseCaseExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({ AccountAlreadyExistException.class })
    public ResponseEntity<Object> handleAccountAlreadyExistException(AccountAlreadyExistException ex, WebRequest request){
        String mensagemToUser = messageSource.getMessage("account.already.exist", null, LocaleContextHolder.getLocale());
        String messageToDev = ExceptionUtils.getRootCauseMessage(ex);
        List<Error> errors = Arrays.asList(new Error(mensagemToUser, messageToDev));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ AccountNotExistException.class })
    public ResponseEntity<Object> handleAccountNotExistException(AccountNotExistException ex, WebRequest request){
        String mensagemToUser = messageSource.getMessage("account.not.exist", null, LocaleContextHolder.getLocale());
        String messageToDev = ExceptionUtils.getRootCauseMessage(ex);
        List<Error> errors = Arrays.asList(new Error(mensagemToUser, messageToDev));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ BalanceCouldNotBeNegative.class })
    public ResponseEntity<Object> handleBalanceCouldNotBeNegative(BalanceCouldNotBeNegative ex, WebRequest request){
        String mensagemToUser = messageSource.getMessage("balance.could.not.be.negative", null, LocaleContextHolder.getLocale());
        String messageToDev = ExceptionUtils.getRootCauseMessage(ex);
        List<Error> errors = Arrays.asList(new Error(mensagemToUser, messageToDev));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ TransactionValueNotInformed.class })
    public ResponseEntity<Object> handleTransactionValueNotInformed(TransactionValueNotInformed ex, WebRequest request){
        String mensagemToUser = messageSource.getMessage("transaction.value.not.informed", null, LocaleContextHolder.getLocale());
        String messageToDev = ExceptionUtils.getRootCauseMessage(ex);
        List<Error> errors = Arrays.asList(new Error(mensagemToUser, messageToDev));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

//    private List<Error> createErrorList(BindingResult bindingResult) {
//        List<Error> errors = new ArrayList<>();
//
//        for(FieldError fieldError : bindingResult.getFieldErrors()) {
//            String messageToUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
//            String messageToDev = fieldError.toString();
//            errors.add(new Error(messageToUser, messageToDev));
//        }
//        return errors;
//    }

    public static class Error {

        private String messageToUser;
        private String messageToDev;

        public Error(String messageToUser, String messageToDev) {
            this.messageToUser = messageToUser;
            this.messageToDev = messageToDev;
        }
        public String getMessageToUser() {
            return messageToUser;
        }
        public String getMessageToDev() {
            return messageToDev;
        }
    }
}
