package meli.rasec.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = "satelites")
public class SateliteDto {

    private String name;
    private LocationDto location;

}
