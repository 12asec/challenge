package meli.rasec.app.service.imp;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import lombok.extern.slf4j.Slf4j;
import meli.rasec.app.dto.*;
import meli.rasec.app.exception.QuasarFireException;
import meli.rasec.app.service.ProcessRequestService;
import meli.rasec.app.service.TopSecretService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TopSecretServiceImp implements TopSecretService {

    @Value("${firestore.collection}")
    private String collection;

    private ProcessRequestService processRequestService;

    private final Firestore firestore;

    public TopSecretServiceImp(Firestore firestore,ProcessRequestService process){
        this.firestore = firestore;
        this.processRequestService = process;
    }

    private CollectionReference getCollection() {
        return this.firestore.collection(collection);
    }

    public AlianzaResponseDto getTopSecret(String collectionId) throws QuasarFireException {

        try {
            CollectionReference collectionReference = this.getCollection();
            DocumentReference document = collectionReference.document(collectionId);

            TopSecretDto topSecretObj = document.get().get().toObject(TopSecretDto.class);

            if (topSecretObj.getPorcentajeCarga()==100) {

                AlianzaRequestDto request = new AlianzaRequestDto();
                request.setSatellites(topSecretObj.getSatellites());

                AlianzaResponseDto salida = processRequestService.process(request);
                return salida;
            } else {
                throw new QuasarFireException("No hay suficiente informacion.");
            }

        } catch (Exception e) {
            throw new QuasarFireException("Error al obtener TopSecret. " + e.getMessage());
        }

    }

    public AlianzaResponseDto WriteTopSecret(String collectionId, SatelliteSplitDto obj, String nameSatellite) throws QuasarFireException {

        TopSecretDto topSecretObj = null;
        Integer porcentaje = 0;

        try {

            CollectionReference collectionReference = this.getCollection();
            DocumentReference document = collectionReference.document(collectionId);
            topSecretObj = document.get().get().toObject(TopSecretDto.class);

            if(topSecretObj!=null){
                List<SatelliteDto> satelliteDto = topSecretObj.getSatellites();
                if(satelliteDto!=null && satelliteDto.size()>0 && satelliteDto.size()<4){
                    porcentaje = topSecretObj.getSatellites().size()*100/3;
                    topSecretObj.setPorcentajeCarga(porcentaje);
                    ApiFuture<WriteResult> collectionsApiFuture = collectionReference.document(collectionId).set(topSecretObj);
                    log.info("Write Firebase Ok {}");
                } else if (satelliteDto==null){
                    SatelliteDto satelliteDtoAux = new SatelliteDto();
                    satelliteDtoAux.setName(nameSatellite);
                    satelliteDtoAux.setDistance(obj.getDistance());
                    satelliteDtoAux.setMessage(obj.getMessage());

                    List<SatelliteDto> satelliteDtoInit = new ArrayList<>();
                    satelliteDtoInit.add(satelliteDtoAux);
                    topSecretObj.setSatellites(satelliteDtoInit);
                    porcentaje = topSecretObj.getSatellites().size()*100/3;
                    topSecretObj.setPorcentajeCarga(porcentaje);
                    ApiFuture<WriteResult> collectionsApiFuture = collectionReference.document(collectionId).set(topSecretObj);
                    log.info("Write Firebase Ok {}");
                } else if (satelliteDto.size()<0 || satelliteDto.size()>4){
                    throw new QuasarFireException("Error al configurar distancias a satelites. Cantidad Satellites superado.");
                }
            } else {
                TopSecretDto topSecretObjInit = new TopSecretDto();
                topSecretObjInit.setId(collectionId);
                SatelliteDto satelliteDtoAux = new SatelliteDto();
                satelliteDtoAux.setName(nameSatellite);
                satelliteDtoAux.setDistance(obj.getDistance());
                satelliteDtoAux.setMessage(obj.getMessage());

                List<SatelliteDto> satelliteDtoInit = new ArrayList<>();
                satelliteDtoInit.add(satelliteDtoAux);
                topSecretObjInit.setSatellites(satelliteDtoInit);
                porcentaje = topSecretObjInit.getSatellites().size()*100/3;
                topSecretObjInit.setPorcentajeCarga(porcentaje);
                ApiFuture<WriteResult> collectionsApiFuture = collectionReference.document(collectionId).set(topSecretObjInit);
                log.info("Write Firebase Ok {}");

                topSecretObj = topSecretObjInit;
            }

        } catch (Exception e) {
            log.error("Error al escribir en Firestore " + e.getMessage());
            throw new QuasarFireException("Error al escribir en Firestore. " + e.getMessage());
        }
        if (porcentaje == 100) {
            AlianzaRequestDto request = new AlianzaRequestDto();
            request.setSatellites(topSecretObj.getSatellites());

            AlianzaResponseDto salida = processRequestService.process(request);
            return salida;
        } else {
            throw new QuasarFireException("No hay suficiente informacion. ");
        }
    }
}
