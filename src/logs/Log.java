package logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    public void CreateLog(String logMessage){
        try {
            File log = new File("C://challenge-conversor-moeda//conversor-moeda//src//logs//logs.txt");
            if (!log.exists()) log.createNewFile();

            FileWriter fw = new FileWriter(log, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(logMessage);
            bw.newLine();
            bw.close();
            fw.close();

        }catch(IOException ex){
            System.out.println("Não foi possível criar o arquivo no path inserido.");
        }
    }

}
