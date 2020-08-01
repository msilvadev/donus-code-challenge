package br.com.donuscodechallenge.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class CreatedResourceEvent extends ApplicationEvent{

    private static final long serialVersionUID = 1L;

    private HttpServletResponse response;
    private String cpf;

    public CreatedResourceEvent(Object source, HttpServletResponse response, String cpf) {
        super(source);
        this.response= response;
        this.cpf = cpf;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public String getCpf() {
        return cpf;
    }
}