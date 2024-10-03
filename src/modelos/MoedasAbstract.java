package modelos;

import api.ApiConversor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import writer.MoedasWriter;

import java.io.FileReader;
import java.util.Map;
import java.util.Set;

public abstract class MoedasAbstract {
    public static Map<String,Double> moedas;

    public MoedasAbstract() {
    }

    public static Map<String, Double> getMoedas() {
        return moedas;
    }

    public static void setMoedas(Map<String, Double> moedas) {
        MoedasAbstract.moedas = moedas;
    }

    public static void ShowKeys(){
        Set<String> keys = moedas.keySet();
        int chavesPorLinha = 3; // Número de chaves por linha
        int contador = 0;

        System.out.println("Códigos de Moedas disponíveis:");
        for (String chave : keys) {
            System.out.print(String.format("Código:%s\t\t", chave));
            contador++;
            if (contador % chavesPorLinha == 0) {
                System.out.println(); // Quebra de linha após cada 3 chaves
            }
        }
    }
}
