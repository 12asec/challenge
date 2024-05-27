package meli.rasec.app.service.imp;

import meli.rasec.app.config.SateliteAlianzaConfig;
import meli.rasec.app.dto.*;
import meli.rasec.app.exception.QuasarFireException;
import meli.rasec.app.service.CommunicationService;
import meli.rasec.app.service.ProcessRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessRequestServiceImp implements ProcessRequestService {

    private CommunicationService communicationService;

    public ProcessRequestServiceImp(CommunicationService communicationServiceIn){
        this.communicationService = communicationServiceIn;
    }

    /**
     * Funcion que procesa request para obtener ubicacion y mensajeria secreta
     * @param alianzaRequestDtoIn request del emisor con formato acordado
     * @return AlianzaResponseDto
     * @throws meli.rasec.app.exception.QuasarFireException Excepcion cuando hay problemas para obtener ubicacion y mensaje secreto
     * @author <a href="mailto:cesarnt@gmail.com">Cesar Nunez T</a>
     * @version 1.0.0
     *
     */
    public AlianzaResponseDto process(AlianzaRequestDto alianzaRequestDtoIn) throws QuasarFireException {

        String[][] messages = alianzaRequestDtoIn.getSatellites().stream().map(SatelliteDto::getMessage).toArray(String[][]::new);
        SateliteAlianzaConfig config = new SateliteAlianzaConfig();
        config.setSatelitesConfig("{\"satelites\":[{\"name\": \"KENOBI\",\"location\": {\"x\": -500,\"y\": -200}},{\"name\":\"SKYWALKER\",\"location\":{\"x\":100,\"y\":-100}},{\"name\":\"SATO\",\"location\":{\"x\":500,\"y\":100}}]}");

        List<SateliteDto> satellites = config.getInitLocations();

        float distance1 = alianzaRequestDtoIn.getSatellites().stream()
                .filter(obj -> obj.getName().equalsIgnoreCase(satellites.get(0).getName()))
                .findFirst()
                .orElseThrow(()->new QuasarFireException("Se desconoce satelite enviado"))
                .getDistance();

        float distance2 = alianzaRequestDtoIn.getSatellites().stream()
                .filter(obj -> obj.getName().equalsIgnoreCase(satellites.get(1).getName()))
                .findFirst()
                .orElseThrow(()->new QuasarFireException("Se desconoce satelite enviado"))
                .getDistance();

        float distance3 = alianzaRequestDtoIn.getSatellites().stream()
                .filter(obj -> obj.getName().equalsIgnoreCase(satellites.get(2).getName()))
                .findFirst()
                .orElseThrow(()->new QuasarFireException("Se desconoce satelite enviado"))
                .getDistance();


        float[] ubicacion = communicationService.getLocation(distance1,distance2,distance3);
        String finalMessage = communicationService.getMessage(messages);

        AlianzaResponseDto salida = new AlianzaResponseDto();
        salida.setMessage(finalMessage);
        PositionDto positionDto = new PositionDto();
        positionDto.setX(ubicacion[0]);
        positionDto.setY(ubicacion[1]);
        salida.setPosition(positionDto);

        return salida;
    }

}
