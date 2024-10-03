package modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataFormatter {

    public String Formatar(String formato){
        String dataFormatada;
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return  dataFormatada = agora.format(formatter);
    }
}
