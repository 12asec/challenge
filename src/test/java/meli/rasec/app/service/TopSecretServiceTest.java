package meli.rasec.app.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import meli.rasec.app.dto.*;
import meli.rasec.app.exception.QuasarFireException;
import meli.rasec.app.service.imp.TopSecretServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.util.Assert;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TopSecretServiceTest {

    @Mock
    Firestore firestore;
    @Mock
    CollectionReference collectionReference;
    @Mock
    DocumentReference documentReference;

    @Mock
    ApiFuture<DocumentSnapshot> documentSnapshotApi;

    @Mock
    DocumentSnapshot documentSnapshot;

    @Mock
    ProcessRequestService processRequestService;
    private TopSecretService topSecretService;

    private TopSecretDto topSecretObj;

    @BeforeEach
    void setUp() {
        openMocks(this);
        topSecretService = new TopSecretServiceImp(firestore, processRequestService);

        AlianzaResponseDto alianzaResponseDto = new AlianzaResponseDto();
        alianzaResponseDto.setMessage("esto es secreto");
        PositionDto positionDto = new PositionDto();
        positionDto.setX(200);
        positionDto.setY(100);
        alianzaResponseDto.setPosition(positionDto);

        topSecretObj = new TopSecretDto();
        topSecretObj.setPorcentajeCarga(100);
        topSecretObj.setId("collectId");
        List<SatelliteDto> satelliteDtoList = new ArrayList<>();
        SatelliteDto satelliteDto = new SatelliteDto();
        satelliteDto.setName("KENOBI");
        satelliteDto.setDistance(200);
        String[] text = {"asd","", ""};
        satelliteDto.setMessage(text);
        satelliteDtoList.add(satelliteDto);

        SatelliteDto satelliteDto1 = new SatelliteDto();
        satelliteDto1.setName("SKYWALKER");
        satelliteDto1.setDistance(300);
        String[] text1 = {"","asd", ""};
        satelliteDto1.setMessage(text1);
        satelliteDtoList.add(satelliteDto1);

        SatelliteDto satelliteDto2 = new SatelliteDto();
        satelliteDto2.setName("SATO");
        satelliteDto2.setDistance(300);
        String[] text2 = {"","","asd"};
        satelliteDto2.setMessage(text2);
        satelliteDtoList.add(satelliteDto2);

        topSecretObj.setSatellites(satelliteDtoList);
    }

    @Test
    void getTopSecretOk() throws ExecutionException, InterruptedException {
        when(firestore.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(anyString())).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(documentSnapshotApi);
        when(documentSnapshotApi.get()).thenReturn(documentSnapshot);
        when(documentSnapshot.toObject(any())).thenReturn(topSecretObj);

        Assertions.assertDoesNotThrow(()->{
            topSecretService.getTopSecret("collectId");
        });
    }

    @Test
    void getTopSecretExc() {
        when(firestore.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(any())).thenThrow(new NullPointerException("nulo"));

        QuasarFireException ex = Assertions.assertThrows(QuasarFireException.class, ()->{
            topSecretService.getTopSecret("collectId");
        });

        Assertions.assertTrue(ex.getMessage().contains("nulo"));
    }

    @Test
    void writeTopSecretOk() throws ExecutionException, InterruptedException {
        when(firestore.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(anyString())).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(documentSnapshotApi);
        when(documentSnapshotApi.get()).thenReturn(documentSnapshot);
        when(documentSnapshot.toObject(any())).thenReturn(topSecretObj);

        SatelliteSplitDto obj = new SatelliteSplitDto();
        obj.setDistance(200);
        String[] text = {"hola", "como", "asd"};
        obj.setMessage(text);

        Assertions.assertDoesNotThrow(()->{
            topSecretService.WriteTopSecret("collectId", obj, "KENOBI");
        });
    }

    @Test
    void writeTopSecretSatNull() throws ExecutionException, InterruptedException {
        when(firestore.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(anyString())).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(documentSnapshotApi);
        when(documentSnapshotApi.get()).thenReturn(documentSnapshot);
        topSecretObj.setSatellites(null);
        when(documentSnapshot.toObject(any())).thenReturn(topSecretObj);

        SatelliteSplitDto obj = new SatelliteSplitDto();
        obj.setDistance(200);
        String[] text = {"hola", "como", "asd"};
        obj.setMessage(text);

        QuasarFireException ex = Assertions.assertThrows(QuasarFireException.class, ()->{
            topSecretService.WriteTopSecret("collectId", obj, "KENOBI");
        });

        Assertions.assertTrue(ex.getMessage().contains("suficiente"));
    }

    @Test
    void writeTopSecretTopSecretNull() throws ExecutionException, InterruptedException {
        when(firestore.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(anyString())).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(documentSnapshotApi);
        when(documentSnapshotApi.get()).thenReturn(documentSnapshot);
        topSecretObj = null;
        when(documentSnapshot.toObject(any())).thenReturn(topSecretObj);

        SatelliteSplitDto obj = new SatelliteSplitDto();
        obj.setDistance(200);
        String[] text = {"hola", "como", "asd"};
        obj.setMessage(text);

        QuasarFireException ex = Assertions.assertThrows(QuasarFireException.class, ()->{
            topSecretService.WriteTopSecret("collectId", obj, "KENOBI");
        });

        Assertions.assertTrue(ex.getMessage().contains("suficiente"));
    }

    @Test
    void writeTopSecretExc() throws ExecutionException, InterruptedException {
        when(firestore.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(any())).thenThrow(new NullPointerException("nulo"));

        SatelliteSplitDto obj = new SatelliteSplitDto();
        obj.setDistance(200);
        String[] text = {"hola", "como", "asd"};
        obj.setMessage(text);

        QuasarFireException ex = Assertions.assertThrows(QuasarFireException.class, ()->{
            topSecretService.WriteTopSecret("collectId", obj, "KENOBI");
        });

        Assertions.assertTrue(ex.getMessage().contains("nulo"));
    }
}