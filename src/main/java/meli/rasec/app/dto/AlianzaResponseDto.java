package meli.rasec.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties
public class AlianzaResponseDto {
    private PositionDto position;
    private String message;
}
