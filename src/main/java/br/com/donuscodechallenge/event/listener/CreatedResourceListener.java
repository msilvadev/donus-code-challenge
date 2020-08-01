package br.com.donuscodechallenge.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import br.com.donuscodechallenge.event.CreatedResourceEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class CreatedResourceListener implements ApplicationListener<CreatedResourceEvent> {

    @Override
    public void onApplicationEvent(CreatedResourceEvent event) {
        HttpServletResponse response = event.getResponse();
        String cpf = event.getCpf();

        adicionarHeaderLcation(response, cpf);
    }

    private void adicionarHeaderLcation(HttpServletResponse response, String cpf) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{cpf}")
                .buildAndExpand(cpf).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }

}