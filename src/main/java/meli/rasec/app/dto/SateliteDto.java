package meli.rasec.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static meli.rasec.app.Application.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SateliteDto {

    private String name;
    private int x;
    private int y;

}
