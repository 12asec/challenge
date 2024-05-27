package meli.rasec.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

import lombok.AllArgsConstructor;


@Getter
@Setter
public class AlianzaRequestDto {

    @NotNull(message = "Satellites no puede ser nulo")
    List<SatelliteDto> satellites;
}
