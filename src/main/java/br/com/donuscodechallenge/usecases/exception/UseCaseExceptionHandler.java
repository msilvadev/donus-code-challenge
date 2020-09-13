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
        String messageUser = messageSource.getMessage("account.already.exist", null, LocaleContextHolder.getLocale());
        String messageDev = ExceptionUtils.getRootCauseMessage(ex);
        List<Error> errors = Arrays.asList(new Error(messageUser, messageDev));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ AccountNotExistException.class })
    public ResponseEntity<Object> handleAccountNotExistException(AccountNotExistException ex, WebRequest request){
        Object[] cpfWhichNotExist = new Object[]{
                ex.getMessage()
        };
        String messageUser = messageSource.getMessage("account.not.exist", cpfWhichNotExist, LocaleContextHolder.getLocale());
        String messageDev = ExceptionUtils.getRootCauseMessage(ex);
        List<Error> errors = Arrays.asList(new Error(messageUser, messageDev));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ BalanceCouldNotBeNegative.class })
    public ResponseEntity<Object> handleBalanceCouldNotBeNegative(BalanceCouldNotBeNegative ex, WebRequest request){
        String messageUser = messageSource.getMessage("balance.could.not.be.negative", null, LocaleContextHolder.getLocale());
        String messageDev = ExceptionUtils.getRootCauseMessage(ex);
        List<Error> errors = Arrays.asList(new Error(messageUser, messageDev));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ TransactionValueNotInformed.class })
    public ResponseEntity<Object> handleTransactionValueNotInformed(TransactionValueNotInformed ex, WebRequest request){
        String messageUser = messageSource.getMessage("transaction.value.not.informed", null, LocaleContextHolder.getLocale());
        String messageToDev = ExceptionUtils.getRootCauseMessage(ex);
        List<Error> errors = Arrays.asList(new Error(messageUser, messageToDev));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    public static class Error {

        private String messageUser;
        private String messageDev;

        public Error(String messageUser, String messageDev) {
            this.messageUser = messageUser;
            this.messageDev = messageDev;
        }
        public String getMessageUser() {
            return messageUser;
        }
        public String getMessageDev() {
            return messageDev;
        }
    }
}
