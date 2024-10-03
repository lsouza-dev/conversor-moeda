package writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelos.Moedas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MoedasWriter {
    private String fileName = "moedas.json";

    public void Write(Moedas moeda) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if (moeda.conversion_rates() == null) throw new NullPointerException("A moeda escolhida não foi encontrada para realizar a conversão.\nVerifique o código da moeda inserida e tente novamente.");
            var moedas = gson.toJson(moeda.conversion_rates());

            FileWriter writer = new FileWriter(fileName);
            writer.write(moedas);
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
