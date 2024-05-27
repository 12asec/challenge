package meli.rasec.app.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SatelliteSplitDto {
    @NotNull
    @Digits(integer = Float.SIZE - 1, fraction = 1, message = "Distance debe poseer solo un decimal y debe contener un maximo de 32 caracteres")
    private float distance;
    @NotNull
    private String[] message;
}
