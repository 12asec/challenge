package meli.rasec.app.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class TopSecretDto {

    private String Id;
    @NotNull(message = "Satellites no puede ser nulo")
    private List<SatelliteDto> satellites;
    private Integer porcentajeCarga;

}
