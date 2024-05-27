package meli.rasec.app.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import meli.rasec.app.dto.InitConfigDto;
import meli.rasec.app.dto.LocationDto;
import meli.rasec.app.dto.SateliteDto;
import meli.rasec.app.exception.QuasarFireException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static meli.rasec.app.Application.*;

@Component
@Configuration
@Setter
public class SateliteAlianzaConfig {

    /**
     * Variable de entorno de la posicion en la galaxia de los satelites de la alianza Kenobi, Skywalker y Sato.
     *
     * @author <a href="mailto:cesarnt@gmail.com">Cesar Nunez T</a>
     * @version 1.0
     */
    @Value("${quasarfire.init.location}")
    private String satelitesConfig;

    /**
     * Funcion que retorna la posicion inicial de los satelites de la alianza, para obtencion de la ubicacion.
     *
     * @return Ubicacion de los 3 satelites.
     * @throws QuasarFireException
     * @author <a href="mailto:cesarnt@gmail.com">Cesar Nunez T</a>
     * @version 1.0
     */


    public List<SateliteDto> getInitLocations() throws QuasarFireException {

        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            InitConfigDto satelitesInit = mapper.readValue(satelitesConfig, InitConfigDto.class);

            if(satelitesInit.getSatelites()!=null) {
                if(satelitesInit.getSatelites().size() == 3) {
                    satelitesInit.getSatelites().forEach(satelite -> {

                        String nombre = satelite.getName();

                        if (satelite.getLocation() == null) {
                            try {
                                throw new QuasarFireException("Se debe configurar ubicacion inicial del satelite " + nombre);
                            } catch (QuasarFireException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    });
                } else {
                    throw new QuasarFireException("Se debe configurar ubicacion inicial de los 3 satelites iniciales");
                }
            } else {
                throw new QuasarFireException("Se debe configurar ubicacion inicial de satelites Kenobi, Skywalker y Sato.");
            }

            return satelitesInit.getSatelites();

        } catch (Exception ex) {
            throw new QuasarFireException(ex.getMessage());
        }
    }
}
