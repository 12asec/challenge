package meli.rasec.app.exception;

/**
 *
 * Clase que define Excepciones particulares al programa QUASAR FIRE.
 *
 * @author <a href="mailto:cesarnt@gmail.com">Cesar Nunez T</a>
 * @version 1.0
 * @see Exception
 *
 * */
public class QuasarFireException extends Exception {

    private static final long serialVersionUID = -111222333L;

    public QuasarFireException(String message) {
        super(message);
    }

}
