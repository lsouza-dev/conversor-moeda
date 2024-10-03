package logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    public void CreateLog(String logMessage){
        File log = new File("C://challenge-conversor-moeda//conversor-moeda//src//logs//logs.txt");
        if (!log.exists()) {
            try {
                log.createNewFile();
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(logMessage);
                bw.newLine();
                bw.close();
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
