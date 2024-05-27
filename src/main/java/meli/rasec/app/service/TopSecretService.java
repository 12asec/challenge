package meli.rasec.app.service;

import meli.rasec.app.dto.AlianzaResponseDto;
import meli.rasec.app.dto.SatelliteSplitDto;
import meli.rasec.app.exception.QuasarFireException;

public interface TopSecretService {

    AlianzaResponseDto getTopSecret(String collectionId) throws QuasarFireException;
    AlianzaResponseDto WriteTopSecret(String collectionId, SatelliteSplitDto obj, String nameSatellite) throws QuasarFireException;
    
}
