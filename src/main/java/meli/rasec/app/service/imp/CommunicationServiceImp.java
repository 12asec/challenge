package meli.rasec.app.service.imp;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import meli.rasec.app.config.SateliteAlianzaConfig;
import meli.rasec.app.dto.SateliteDto;
import meli.rasec.app.exception.QuasarFireException;
import meli.rasec.app.service.CommunicationService;
import meli.rasec.app.util.NumberUtils;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static meli.rasec.app.Application.SATELITES_FALTANTES;

/**
 * Implementacion de CommunicationService. Aqui encontraras los metodos asociados
 * a la comunicacion entre los aliados de la alianza rebelde.
 *
 * @author <a href="mailto:cesarnt@gmail.com">Cesar Nunez T</a>
 * @version 1.0.0
 *
 * */
@Service
@Component
public class CommunicationServiceImp implements CommunicationService {

    SateliteAlianzaConfig sateliteAlianzaConfig;
    public CommunicationServiceImp(SateliteAlianzaConfig sateliteAlianzaConfigIn){
        this.sateliteAlianzaConfig = sateliteAlianzaConfigIn;
    }
    /**
     * Metodo que retorna la posicion en la galaxia de un aliado, basado en la triangulacion de satelites.
     * @param distances distancias a la que se encuentra el emisor a cada satelite de la Alianza.
     * @return Arreglo con la posicion (x,y) del emisor en la galaxia.
     * @see LeastSquaresOptimizer
     * @see TrilaterationFunction
     * @throws QuasarFireException
     */
    public float[] getLocation(float... distances) throws QuasarFireException {

        try{
            if(distances.length < 3){
                throw new QuasarFireException(SATELITES_FALTANTES);
            }

            List<SateliteDto> satelitesAlianza = sateliteAlianzaConfig.getInitLocations();

            double[][] positions = satelitesAlianza.stream().map(s-> new double[]{s.getLocation().getX(),s.getLocation().getY()})
                    .toList().toArray(new double[0][0]);

            NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(
                    new TrilaterationFunction(positions, NumberUtils.toDoubleArray(distances))
                    , new LevenbergMarquardtOptimizer());
            LeastSquaresOptimizer.Optimum optimum = solver.solve();

            double[] posicionFinal = optimum.getPoint().toArray();

            return new float[]{ NumberUtils.redondear(posicionFinal[0],1)
                    , NumberUtils.redondear(posicionFinal[1],1) };

        } catch (Exception ex) {
            throw new QuasarFireException(ex.getMessage());
        }
    }

    /**
     * Metodo que permite obtener el mensaje completo generado por el emisor, debido al desfase causado
     * por los asteroides entre el emisor y cada satelite.
     *
     * @param messages Arreglo con mensaje obtenido en cada satelite.
     * @return Mensaje completo generado por el emisor.
     * @throws QuasarFireException
     */
    public String getMessage(String[]... messages) throws QuasarFireException {
        int tamano = Stream.of(messages).mapToInt(mensaje -> mensaje.length).min().orElse(0);

        if(tamano<=0){
            throw new QuasarFireException("No es posible determinar el largo del mensaje");
        }

        String[] messageObj = Stream.of(messages).reduce(new String[tamano], (left, right) -> {
           int var = right.length - left.length;

           for (int aux = 0; aux < left.length; ++aux){
               if (left[aux] == null && !right[aux + var].trim().isEmpty())
                   left[aux] = right[aux + var];
           }
           return left;
        });

        if (Stream.of(messageObj).anyMatch(Objects::isNull)){
            throw new QuasarFireException("No es posible determinar el mensaje completo.");
        }
        return String.join(" ", messageObj);
    }

}
