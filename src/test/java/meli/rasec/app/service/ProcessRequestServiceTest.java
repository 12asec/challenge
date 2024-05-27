package meli.rasec.app.service;

import meli.rasec.app.dto.AlianzaRequestDto;
import meli.rasec.app.dto.SatelliteDto;
import meli.rasec.app.exception.QuasarFireException;
import meli.rasec.app.service.imp.ProcessRequestServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ProcessRequestServiceTest {

    private ProcessRequestService processRequestService;

    @Mock
    private CommunicationService communicationService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        processRequestService = new ProcessRequestServiceImp(communicationService);
    }

    @Test
    void processOk() throws QuasarFireException {
        float[] ubi = {200, 100, 300};
        String mes = "este es un mensaje secreto";
        when(communicationService.getLocation(anyFloat(),anyFloat(),anyFloat())).thenReturn(ubi);
        when(communicationService.getMessage(any())).thenReturn(mes);

        Assertions.assertDoesNotThrow(()->{
            AlianzaRequestDto alianzaRequestDto = new AlianzaRequestDto();
            List<SatelliteDto> lista = new ArrayList<>();
            SatelliteDto obj = new SatelliteDto();
            String[] text = {"hola"};
            obj.setMessage(text);
            obj.setDistance(100);
            obj.setName("KENOBI");
            lista.add(obj);

            SatelliteDto obj1 = new SatelliteDto();
            String[] text1 = {"hola"};
            obj1.setMessage(text1);
            obj1.setDistance(200);
            obj1.setName("SKYWALKER");
            lista.add(obj1);

            SatelliteDto obj2 = new SatelliteDto();
            String[] text2 = {"hola"};
            obj2.setMessage(text2);
            obj2.setDistance(300);
            obj2.setName("SATO");
            lista.add(obj2);

            alianzaRequestDto.setSatellites(lista);

            processRequestService.process(alianzaRequestDto);

        });
    }

    @Test
    void processEx() throws QuasarFireException {

        when(communicationService.getLocation(anyFloat(),anyFloat(),anyFloat())).thenThrow(new QuasarFireException("quasar"));

        QuasarFireException ex = Assertions.assertThrows(QuasarFireException.class, ()->{
            AlianzaRequestDto alianzaRequestDto = new AlianzaRequestDto();
            List<SatelliteDto> lista = new ArrayList<>();
            SatelliteDto obj = new SatelliteDto();
            String[] text = {"hola"};
            obj.setMessage(text);
            obj.setDistance(100);
            obj.setName("KENOBI");
            lista.add(obj);

            SatelliteDto obj1 = new SatelliteDto();
            String[] text1 = {"hola"};
            obj1.setMessage(text1);
            obj1.setDistance(200);
            obj1.setName("SKYWALKER");
            lista.add(obj1);

            SatelliteDto obj2 = new SatelliteDto();
            String[] text2 = {"hola"};
            obj2.setMessage(text2);
            obj2.setDistance(300);
            obj2.setName("SATO");
            lista.add(obj2);

            alianzaRequestDto.setSatellites(lista);

            processRequestService.process(alianzaRequestDto);
        });

        Assertions.assertTrue(ex.getMessage().contains("quasar"));
    }
}