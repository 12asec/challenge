package meli.rasec.app.controller;

import meli.rasec.app.dto.AlianzaResponseDto;
import meli.rasec.app.dto.PositionDto;
import meli.rasec.app.dto.SatelliteSplitDto;
import meli.rasec.app.exception.QuasarFireException;
import meli.rasec.app.service.TopSecretService;
import meli.rasec.app.valid.ValidHeaders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TopSecretSplitControllerTest {

    private TopSecretSplitController topSecretSplitController;

    private HttpHeaders httpHeaders;

    @Mock
    private ValidHeaders validHeaders;

    @Mock
    private TopSecretService topSecretService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        topSecretSplitController = new TopSecretSplitController(validHeaders, topSecretService);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-star", "starwar");
        httpHeaders.add("X-common", "comun");
        httpHeaders.add("X-user-id", "12asec");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }

    @Test
    void postOk() throws QuasarFireException {
        AlianzaResponseDto alianzaResponseDto = new AlianzaResponseDto();
        alianzaResponseDto.setMessage("esto es secreto");
        PositionDto positionDto = new PositionDto();
        positionDto.setX(200);
        positionDto.setY(100);
        alianzaResponseDto.setPosition(positionDto);

        when(validHeaders.validHeaders(any(HttpHeaders.class))).thenReturn(true);
        when(topSecretService.WriteTopSecret(anyString(), any(SatelliteSplitDto.class), anyString())).thenReturn(alianzaResponseDto);

        Assertions.assertDoesNotThrow(()->{

            SatelliteSplitDto satelliteSplitDto = new SatelliteSplitDto();
            satelliteSplitDto.setDistance(200);
            String[] text = {"secreto"};
            satelliteSplitDto.setMessage(text);
            topSecretSplitController.post("KENOBI", satelliteSplitDto,httpHeaders);

        });
    }

    @Test
    void postExc() throws QuasarFireException {
        AlianzaResponseDto alianzaResponseDto = new AlianzaResponseDto();
        alianzaResponseDto.setMessage("esto es secreto");
        PositionDto positionDto = new PositionDto();
        positionDto.setX(200);
        positionDto.setY(100);
        alianzaResponseDto.setPosition(positionDto);

        when(validHeaders.validHeaders(any(HttpHeaders.class))).thenReturn(true);
        when(topSecretService.WriteTopSecret(anyString(), any(SatelliteSplitDto.class), anyString())).thenThrow(new QuasarFireException("quasar"));

        Assertions.assertDoesNotThrow(()->{

            SatelliteSplitDto satelliteSplitDto = new SatelliteSplitDto();
            satelliteSplitDto.setDistance(200);
            String[] text = {"secreto"};
            satelliteSplitDto.setMessage(text);
            topSecretSplitController.post("KENOBI", satelliteSplitDto,httpHeaders);

        });
    }

    @Test
    void postErr() throws QuasarFireException {
        AlianzaResponseDto alianzaResponseDto = new AlianzaResponseDto();
        alianzaResponseDto.setMessage("esto es secreto");
        PositionDto positionDto = new PositionDto();
        positionDto.setX(200);
        positionDto.setY(100);
        alianzaResponseDto.setPosition(positionDto);

        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-star", "starwar");
        httpHeaders.add("X-common", "comun");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        when(validHeaders.validHeaders(any(HttpHeaders.class))).thenReturn(true);
        when(topSecretService.WriteTopSecret(anyString(), any(SatelliteSplitDto.class), anyString())).thenReturn(alianzaResponseDto);

        Assertions.assertDoesNotThrow(()->{

            SatelliteSplitDto satelliteSplitDto = new SatelliteSplitDto();
            satelliteSplitDto.setDistance(200);
            String[] text = {"secreto"};
            satelliteSplitDto.setMessage(text);
            topSecretSplitController.post("KENOBI", satelliteSplitDto,httpHeaders);

        });
    }

    @Test
    void getOk() throws QuasarFireException {
        AlianzaResponseDto alianzaResponseDto = new AlianzaResponseDto();
        alianzaResponseDto.setMessage("esto es secreto");
        PositionDto positionDto = new PositionDto();
        positionDto.setX(200);
        positionDto.setY(100);
        alianzaResponseDto.setPosition(positionDto);

        when(validHeaders.validHeaders(any(HttpHeaders.class))).thenReturn(true);
        when(topSecretService.getTopSecret(anyString())).thenReturn(alianzaResponseDto);

        Assertions.assertDoesNotThrow(()->{
            topSecretSplitController.get(httpHeaders);
        });
    }

    @Test
    void getExp() throws QuasarFireException {
        AlianzaResponseDto alianzaResponseDto = new AlianzaResponseDto();
        alianzaResponseDto.setMessage("esto es secreto");
        PositionDto positionDto = new PositionDto();
        positionDto.setX(200);
        positionDto.setY(100);
        alianzaResponseDto.setPosition(positionDto);

        when(validHeaders.validHeaders(any(HttpHeaders.class))).thenReturn(true);
        when(topSecretService.getTopSecret(anyString())).thenThrow(new QuasarFireException("quasar"));

        Assertions.assertDoesNotThrow(()->{
            topSecretSplitController.get(httpHeaders);
        });
    }

    @Test
    void getErr() throws QuasarFireException {
        AlianzaResponseDto alianzaResponseDto = new AlianzaResponseDto();
        alianzaResponseDto.setMessage("esto es secreto");
        PositionDto positionDto = new PositionDto();
        positionDto.setX(200);
        positionDto.setY(100);
        alianzaResponseDto.setPosition(positionDto);

        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-star", "starwar");
        httpHeaders.add("X-common", "comun");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        when(validHeaders.validHeaders(any(HttpHeaders.class))).thenReturn(true);
        when(topSecretService.getTopSecret(anyString())).thenReturn(alianzaResponseDto);

        Assertions.assertDoesNotThrow(()->{
            topSecretSplitController.get(httpHeaders);
        });
    }
}