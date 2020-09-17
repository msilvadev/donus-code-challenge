package br.com.donuscodechallenge.metric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 *
 *          IMPLEMENTAR HDR
 *
 *
 * This factory provide a service o manage metrics for your application.
 * Will print on log and will can save in database, where you will be able,
 * retrive this data and show on some dashboard.
 * @author msilvadev
 * @since 2020
 * @version 1.0
 */
@Service
public class MetricsFactory {

    // RECEBER VIA PARAMETRO PARA QUE QUALQUER APLICAÇÃO PASSE O LOGGER QUE ESTIVER UTILIZANDO
    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsFactory.class);
    private long start;

    // PROPERTIES TO ACCESS DATABASE
    public void createMetrics(){}


    /**
     * Analisar possibilidade de criar uma factory
     * @return
     */
    public void start(){
        this.start = System.currentTimeMillis();
    }

    /**
     * ANALISAR POSSIBILIDADE DE ALÉM DE JOGAR NO LOG, SALVAR EM BANCO DE DADOS
     * @param description Descrition for this metric
     */
    public void finish(String description){
        long end = (System.currentTimeMillis() - this.start) / 1_000L;

        LOGGER.trace(description + " executed in {} seconds", end);
    }

}
