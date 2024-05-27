package meli.rasec.app.valid.Imp;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import meli.rasec.app.valid.ValidHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ValidHeadersImp implements ValidHeaders {

    private Map<String, List<String>> requiredHeaders;

    /**
     * Cabecera X-common que valida frase comun entre aliados de la republica
     */
    @Value("${spring.validate.republic}")
    private List<String> commonrepublic;

    /**
     * Cabecera X-star que valida planetas Libres de aliados de la republica
     */
    @Value("${spring.validate.stars}")
    private List<String> planets;

    public ValidHeadersImp(List<String> commonrepublicIn, List<String> planetsIn) {
        this.commonrepublic = commonrepublicIn;
        this.planets = planetsIn;
        setInit();
    }

    public ValidHeadersImp() {
        setInit();
    }

    @PostConstruct
    public void fooInit() {
        setInit();
    }

    private void setInit() {
        requiredHeaders = new HashMap<>();
        if (planets != null && commonrepublic != null) {
            planets.replaceAll(String::toUpperCase);
            commonrepublic.replaceAll(String::toUpperCase);

            requiredHeaders.put("X-common", commonrepublic);
            requiredHeaders.put("X-star", planets);

            log.info("Validate Headers {}", requiredHeaders);
        }
    }

    public boolean validHeaders(HttpHeaders httpHeaders) {
        Boolean validateResult = true;
        for (Map.Entry<String, List<String>> entry : requiredHeaders.entrySet()) {
            String header = entry.getKey();
            List<String> listaValidar = entry.getValue();
            String value = httpHeaders.getFirst(header);
            if (value == null) {
                validateResult = false;
            } else {
                if (Boolean.FALSE.equals(listaValidar.contains(value.toUpperCase()))) {
                    log.error("Validate Headers {} not valid", value);
                    validateResult = false;
                }
            }
        }
        return validateResult;
    }

}
