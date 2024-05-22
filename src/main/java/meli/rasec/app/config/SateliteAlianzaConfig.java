package meli.rasec.app.config;

import meli.rasec.app.dto.SateliteDto;
import meli.rasec.app.exception.QuasarFireException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import static meli.rasec.app.Application.*;

@Configuration
public class SateliteAlianzaConfig {

    /**
     * Variable de entorno de la posicion en la galaxia del satelite Skywalker.
     *
     * @author <a href="mailto:cesarnt@gmail.com">Cesar Nunez T</a>
     * @version 1.0
     */
    @Value("${quasarfire.init.location.skywalker}")
    private static List<Integer> locationInitSky;

    /**
     * Variable de entorno de la posicion en la galaxia del satelite Kenobi.
     *
     * @author <a href="mailto:cesarnt@gmail.com">Cesar Nunez T</a>
     * @version 1.0
     */
    @Value("${quasarfire.init.location.kenobi}")
    private static List<Integer> locationInitKenobi;

    /**
     * Variable de entorno de la posicion en la galaxia del satelite Sato.
     *
     * @author <a href="mailto:cesarnt@gmail.com">Cesar Nunez T</a>
     * @version 1.0
     */
    @Value("${quasarfire.init.location.sato}")
    private static List<Integer> locationInitSato;

    /**
     * Funcion que retorna la posicion inicial de los satelites de la alianza, para obtencion de la ubicacion.
     *
     * @return Ubicacion de los 3 satelites.
     * @throws QuasarFireException
     * @author <a href="mailto:cesarnt@gmail.com">Cesar Nunez T</a>
     * @version 1.0
     */


    public static List<SateliteDto> getInitLocations() throws QuasarFireException {

        try {
            if (locationInitKenobi == null || locationInitKenobi.isEmpty()) {
                throw new QuasarFireException("Se debe configurar ubicacion inicial del satelite Kenobi.");
            }

            if (locationInitSky == null || locationInitSky.isEmpty()) {
                throw new QuasarFireException("Se debe configurar ubicacion inicial del satelite Skywalker.");
            }

            if (locationInitSato == null || locationInitSato.isEmpty()) {
                throw new QuasarFireException("Se debe configurar ubicacion inicial del satelite Sato.");
            }

            if (locationInitKenobi.size() > 2 ) {
                throw new QuasarFireException("Ubicacion inicial de Kenobi posee mas de 2 ejes.");
            }

            if (locationInitKenobi.size() < 2 ) {
                throw new QuasarFireException("Ubicacion inicial de Kenobi posee solo 1 eje.");
            }

            if (locationInitSky.size() > 2 ) {
                throw new QuasarFireException("Ubicacion inicial de Skywalker posee mas de 2 ejes.");
            }

            if (locationInitSky.size() < 2 ) {
                throw new QuasarFireException("Ubicacion inicial de Skywalker posee solo 1 eje.");
            }

            if (locationInitSato.size() > 2 ) {
                throw new QuasarFireException("Ubicacion inicial de Sato posee mas de 2 ejes.");
            }

            if (locationInitSato.size() < 2 ) {
                throw new QuasarFireException("Ubicacion inicial de Sato posee solo 1 eje.");
            }

            List<SateliteDto> salida = new ArrayList<SateliteDto>();
            salida.add(new SateliteDto(KENOBI_NAME, locationInitKenobi.get(0), locationInitKenobi.get(1)));
            salida.add(new SateliteDto(SKYWALKER_NAME, locationInitSky.get(0), locationInitSky.get(1)));
            salida.add(new SateliteDto(SATO_NAME, locationInitSato.get(0), locationInitSato.get(1)));
            return salida;

        } catch (Exception ex) {
            throw new QuasarFireException(ex.getMessage());
        }
    }
}
