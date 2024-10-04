package writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelos.Moedas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class MoedasWriter {

    private String path = "C://challenge-conversor-moeda//conversor-moeda//src//moedas//pesquisadas//%s";

    public void Write(Moedas moeda,String fileName) {
        String fullPath = String.format(path,fileName);
        try {
            Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
            if (moeda.conversion_rates() == null) throw new NullPointerException("A moeda escolhida não foi encontrada para realizar a conversão.\nVerifique o código da moeda inserida e tente novamente.");
            var moedas = gson.toJson(moeda.conversion_rates());

            File file = new File(fullPath);
            if(!file.exists()){
                file.createNewFile();
                FileWriter writer = new FileWriter(fullPath);
                writer.write(moedas);
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPath() {
        return path;
    }
}
