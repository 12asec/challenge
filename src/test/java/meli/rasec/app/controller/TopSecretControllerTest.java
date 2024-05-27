package meli.rasec.app.controller;


import meli.rasec.app.dto.*;
import meli.rasec.app.exception.QuasarFireException;
import meli.rasec.app.service.ProcessRequestService;
import meli.rasec.app.valid.ValidHeaders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TopSecretControllerTest {

    private TopSecretController topSecretController;
    private HttpHeaders httpHeaders;
    @Mock
    private ValidHeaders validheaders;

    @Mock
    private ProcessRequestService processRequestService;

    private AlianzaRequestDto alianzaRequestDto;

    @BeforeEach
    void setUp() {
        openMocks(this);
        topSecretController = new TopSecretController(validheaders,processRequestService);

        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-star", "starwar");
        httpHeaders.add("X-common", "comun");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        alianzaRequestDto = new AlianzaRequestDto();
        List<SatelliteDto> lista = new ArrayList<>();
        SatelliteDto obj = new SatelliteDto();
        String[] text = {"hola"};
        obj.setMessage(text);
        obj.setDistance(100);
        obj.setName("Kenobi");
        lista.add(obj);
        alianzaRequestDto.setSatellites(lista);

    }

    @Test
    void postTopSecretHeaderNOk(){

        when(validheaders.validHeaders(any(HttpHeaders.class))).thenReturn(false);
        Assertions.assertDoesNotThrow(()->{
            topSecretController.postTopSecret(httpHeaders, alianzaRequestDto);
        });
    }

    @Test
    void postTopSecretOk(){

        when(validheaders.validHeaders(any(HttpHeaders.class))).thenReturn(true);
        Assertions.assertDoesNotThrow(()->{
            topSecretController.postTopSecret(httpHeaders, alianzaRequestDto);
        });
    }

    @Test
    void postTopSecretSatellitesNull(){

        when(validheaders.validHeaders(any(HttpHeaders.class))).thenReturn(true);
        alianzaRequestDto.setSatellites(null);
        Assertions.assertDoesNotThrow(()->{
            topSecretController.postTopSecret(httpHeaders, alianzaRequestDto);
        });
    }

    @Test
    void postTopSecretValid() throws QuasarFireException {

        when(validheaders.validHeaders(any(HttpHeaders.class))).thenReturn(true);
        AlianzaResponseDto response = new AlianzaResponseDto();
        PositionDto positionDto = new PositionDto();
        response.setPosition(positionDto);
        response.setMessage("el mensaje es secreto");
        when(processRequestService.process(any(AlianzaRequestDto.class))).thenReturn(response);
        Assertions.assertDoesNotThrow(()->{
            topSecretController.postTopSecret(httpHeaders, alianzaRequestDto);
        });
    }

    @Test
    void postTopSecretQuasarException() throws QuasarFireException {

        when(validheaders.validHeaders(any(HttpHeaders.class))).thenReturn(true);
        when(processRequestService.process(any(AlianzaRequestDto.class))).thenThrow(new QuasarFireException("quasar"));
        Assertions.assertDoesNotThrow(()->{
            topSecretController.postTopSecret(httpHeaders, alianzaRequestDto);
        });
    }
}