package meli.rasec.app.controller;

import lombok.extern.slf4j.Slf4j;
import meli.rasec.app.dto.AlianzaRequestDto;
import meli.rasec.app.dto.AlianzaResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/alianza", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class LocationController {

    @GetMapping("/get/location")
    public ResponseEntity<AlianzaResponseDto> get(@RequestHeader HttpHeaders httpHeaders, @RequestBody AlianzaRequestDto alianzaRequestInput){

        return null;
    }
}
