package meli.rasec.app.service;

import meli.rasec.app.exception.QuasarFireException;

/**
 * Interface que reune los metodos asociados a las Comunicaciones entre los aliados rebeldes.
 * @author <a href="mailto:cesarnt@gmail.com">Cesar Nunez T</a>
 * @version 1.0.0
 */
public interface CommunicationService {

    float[] getLocation(float... distances) throws QuasarFireException;
    String getMessage(String[]... messages) throws QuasarFireException;

}
