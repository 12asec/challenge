package meli.rasec.app.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;

@Getter
@Setter
public class PositionDto {

    @Digits(integer = Float.SIZE - 1, fraction = 1, message = "Posicion X debe poseer solo un decimal y debe contener un maximo de 32 caracteres")
    private float x;
    @Digits(integer = Float.SIZE - 1, fraction = 1, message = "Posicion Y debe poseer solo un decimal y debe contener un maximo de 32 caracteres")
    private float y;
}
