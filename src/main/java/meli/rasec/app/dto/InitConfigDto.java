package meli.rasec.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InitConfigDto {
    public List<SateliteDto> satelites;
}
