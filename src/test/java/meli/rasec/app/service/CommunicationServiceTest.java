package meli.rasec.app.service;

import meli.rasec.app.config.SateliteAlianzaConfig;
import meli.rasec.app.dto.LocationDto;
import meli.rasec.app.dto.SateliteDto;
import meli.rasec.app.dto.SatelliteDto;
import meli.rasec.app.exception.QuasarFireException;
import meli.rasec.app.service.imp.CommunicationServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CommunicationServiceTest {

    private CommunicationService communicationService;

    @Mock
    private SateliteAlianzaConfig sateliteAlianzaConfig;

    @BeforeEach
    void setUp() {
        openMocks(this);
        communicationService = new CommunicationServiceImp(sateliteAlianzaConfig);
    }

    @Test
    void getMessageTamano0(){
        QuasarFireException ex = Assertions.assertThrows( QuasarFireException.class, ()->{
            String[][] mess = {{"hola",""},{"","como"},{}};
            communicationService.getMessage(mess);
        });
        Assertions.assertTrue(ex.getMessage().contains("mensaje"));
    }

    @Test
    void getMessageEx(){
        QuasarFireException ex = Assertions.assertThrows( QuasarFireException.class, ()->{
            String[][] mess = {{"",""},{"","como"}};
            communicationService.getMessage(mess);
        });
        Assertions.assertTrue(ex.getMessage().contains("mensaje"));
    }

    @Test
    void getLocationDistancesErr(){
        QuasarFireException ex = Assertions.assertThrows( QuasarFireException.class, ()->{
            float[] dis = {200,100};
            communicationService.getLocation(dis);
        });
        Assertions.assertTrue(ex.getMessage().contains("satelites"));
    }

    @Test
    void getLocationDistancesQuasarEx() throws QuasarFireException {

        when(sateliteAlianzaConfig.getInitLocations()).thenThrow(new QuasarFireException("quasar"));

        QuasarFireException ex = Assertions.assertThrows( QuasarFireException.class, ()->{
            float[] dis = {200,100,500};
            communicationService.getLocation(dis);
        });
        Assertions.assertTrue(ex.getMessage().contains("quasar"));
    }

    @Test
    void getLocationOk() throws QuasarFireException {

        List<SateliteDto> satelitesAlianza = new ArrayList<>();
        SateliteDto sateliteDto = new SateliteDto();
        LocationDto locationDto = new LocationDto();
        locationDto.setX(200);
        locationDto.setY(100);
        sateliteDto.setLocation(locationDto);
        sateliteDto.setName("Kenobi");
        satelitesAlianza.add(sateliteDto);
        SateliteDto sateliteDto1 = new SateliteDto();
        LocationDto locationDto1 = new LocationDto();
        locationDto1.setX(300);
        locationDto1.setY(200);
        sateliteDto1.setLocation(locationDto1);
        sateliteDto1.setName("Skywalker");
        satelitesAlianza.add(sateliteDto1);
        SateliteDto sateliteDto2 = new SateliteDto();
        LocationDto locationDto2 = new LocationDto();
        locationDto2.setX(300);
        locationDto2.setY(200);
        sateliteDto2.setLocation(locationDto2);
        sateliteDto2.setName("Sato");
        satelitesAlianza.add(sateliteDto2);
        when(sateliteAlianzaConfig.getInitLocations()).thenReturn(satelitesAlianza);
        Assertions.assertDoesNotThrow(()->{
            float[] dis = {200,100,500};
            communicationService.getLocation(dis);
        });
    }
}