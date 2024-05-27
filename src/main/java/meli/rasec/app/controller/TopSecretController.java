package meli.rasec.app.controller;

import lombok.extern.slf4j.Slf4j;
import meli.rasec.app.dto.AlianzaRequestDto;
import meli.rasec.app.dto.AlianzaResponseDto;
import meli.rasec.app.exception.QuasarFireException;
import meli.rasec.app.service.ProcessRequestService;
import meli.rasec.app.valid.ValidHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/topsecret", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class TopSecretController {

    private ValidHeaders validheaders;
    private ProcessRequestService processRequestService;

    @Autowired
    public TopSecretController(ValidHeaders validheaders, ProcessRequestService processRequestService){
        this.processRequestService = processRequestService;
        this.validheaders = validheaders;
    }

    @PostMapping("/")
    public ResponseEntity<AlianzaResponseDto> postTopSecret(@RequestHeader HttpHeaders httpHeadersIn, @RequestBody @Valid AlianzaRequestDto alianzaRequestDtoIn){
        try{
            if (!validheaders.validHeaders(httpHeadersIn)) {
                log.info("Error en Cabeceras");
                return ResponseEntity.badRequest().build();
            }

            if(alianzaRequestDtoIn.getSatellites() != null && alianzaRequestDtoIn.getSatellites().size() > 0) {
                AlianzaResponseDto response = processRequestService.process(alianzaRequestDtoIn);
                return ResponseEntity.ok(response);
            } else {
                log.info("error en el contrato. ");
                return ResponseEntity.badRequest().build();
            }
        } catch (QuasarFireException ex) {
            log.info("QuasarFireException: " + ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
