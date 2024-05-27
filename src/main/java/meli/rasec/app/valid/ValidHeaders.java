package meli.rasec.app.valid;

import org.springframework.http.HttpHeaders;

public interface ValidHeaders {

    boolean validHeaders(HttpHeaders httpHeaders);

}
