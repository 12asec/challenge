package meli.rasec.app.service;

import meli.rasec.app.dto.AlianzaRequestDto;
import meli.rasec.app.dto.AlianzaResponseDto;
import meli.rasec.app.exception.QuasarFireException;

public interface ProcessRequestService {

    AlianzaResponseDto process(AlianzaRequestDto alianzaRequestDtoIn) throws QuasarFireException;

}
