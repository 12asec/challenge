package meli.rasec.app;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import meli.rasec.app.config.SateliteAlianzaConfig;
import meli.rasec.app.exception.QuasarFireException;
import meli.rasec.app.service.CommunicationService;
import meli.rasec.app.service.imp.CommunicationServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * <h3>Fire Quasar App.</h3>
 * <p>Programa perteneciente al servicio de inteligencia rebelde, liderado por
 * el general de la Alianza Han Solo. Estamos en busqueda de dar un gran golpe
 * al Imperio Galactico para reavivar la llama de la resistencia.</p>
 *
 * <code>"Te aseguro que aveces me sorprendo de mi mismo, Han Solo."</code>
 *
 *
 * @author <a href="mailto:cesarnt@gmail.com">Cesar Nunez T</a>
 * @version 1.0.0
 *
 * */
@SpringBootApplication
@Slf4j
@Generated
@ComponentScan(basePackages = "meli.rasec.app")
public class Application{

    public static String KENOBI_NAME = "KENOBI";
    public static String SKYWALKER_NAME = "SKYWALKER";
    public static String SATO_NAME = "SATO";

    public static String SATELITES_FALTANTES = "Se necesita la distancia a uno o mas de nuestros satelites.";

    public static void main (String[] args){

        SpringApplication.run(Application.class, args);

    }
}

