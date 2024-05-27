package meli.rasec.app.valid;

import meli.rasec.app.valid.Imp.ValidHeadersImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidHeadersTest {

    private ValidHeaders validHeaders;
    private HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        List<String> commonrepublicIn = new ArrayList<>();
        commonrepublicIn.add("comun");
        List<String> planetsIn = new ArrayList<>();
        planetsIn.add("tierra");
        validHeaders = new ValidHeadersImp(commonrepublicIn, planetsIn);
    }

    @Test
    void validOk(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-star", "tierra");
        httpHeaders.add("X-common", "comun");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Assertions.assertTrue(validHeaders.validHeaders(httpHeaders));
    }
}