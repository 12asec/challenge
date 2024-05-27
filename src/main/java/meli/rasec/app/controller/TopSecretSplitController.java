package meli.rasec.app.controller;

import lombok.extern.slf4j.Slf4j;
import meli.rasec.app.dto.AlianzaResponseDto;
import meli.rasec.app.dto.SatelliteSplitDto;
import meli.rasec.app.exception.QuasarFireException;
import meli.rasec.app.service.TopSecretService;
import meli.rasec.app.valid.ValidHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/topsecret_split", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class TopSecretSplitController {

    private ValidHeaders validheaders;

    @Autowired
    private TopSecretService topSecretService;

    public TopSecretSplitController(ValidHeaders validheaders, TopSecretService topSecretService){
        this.validheaders = validheaders;
        this.topSecretService = topSecretService;
    }

    @PostMapping("/{satelliteName}")
    public ResponseEntity<AlianzaResponseDto> post(@PathVariable String satelliteName, @RequestBody SatelliteSplitDto satelliteSplitDto, @RequestHeader HttpHeaders httpHeadersIn){
        try{
            if (!validheaders.validHeaders(httpHeadersIn)) {
                log.info("Error en Cabeceras");
                return ResponseEntity.badRequest().build();
            }

            String userId = httpHeadersIn.getFirst("X-user-id");

            if (userId==null) {
                AlianzaResponseDto err = new AlianzaResponseDto();
                err.setMessage("No hay suficiente informacion");
                return ResponseEntity.internalServerError().body(err);
            } else {
                AlianzaResponseDto response = topSecretService.WriteTopSecret(userId, satelliteSplitDto, satelliteName);
                return ResponseEntity.ok(response);
            }

        } catch (QuasarFireException ex) {
            log.info("Error al obtener topsecret: " + ex.getMessage());
            AlianzaResponseDto err = new AlianzaResponseDto();
            err.setMessage("No hay suficiente informacion.");
            return ResponseEntity.internalServerError().body(err);
        }
    }

    @GetMapping("/")
    public ResponseEntity<AlianzaResponseDto> get(@RequestHeader HttpHeaders httpHeadersIn){
        try{
            if (!validheaders.validHeaders(httpHeadersIn)) {
                log.info("Error en Cabeceras");
                return ResponseEntity.badRequest().build();
            }

            String userId = httpHeadersIn.getFirst("X-user-id");

            if (userId==null) {
                AlianzaResponseDto err = new AlianzaResponseDto();
                err.setMessage("No hay suficiente informacion");
                return ResponseEntity.internalServerError().body(err);
            } else {
                AlianzaResponseDto response = topSecretService.getTopSecret(userId);
                return ResponseEntity.ok(response);
            }

        } catch (QuasarFireException ex) {
            log.info("Error al obtener topsecret: " + ex.getMessage());
            AlianzaResponseDto err = new AlianzaResponseDto();
            err.setMessage("No hay suficiente informacion.");
            return ResponseEntity.internalServerError().body(err);
        }
    }
}
