package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelos.Moedas;
import writer.MoedasWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiConversor {
    private String url = "https://v6.exchangerate-api.com/v6/34a8ec3bf4fe297c1cc50ffb/latest/%s";
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;

    public ApiConversor() {
    }

    public HttpResponse<String> RequisicaoApi(String moedaOrigem){
        try {
            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder().uri(URI.create(String.format(url,moedaOrigem))).build();
            response = client.send(request,HttpResponse.BodyHandlers.ofString());

            return this.response;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Moedas GetMoedas(HttpResponse<String> response){
        if(response.statusCode() == 404) throw new IllegalArgumentException("A moeda inserida não existe.\nNão foi possível encontrar a moeda inserida e realizar a conversão.");
        try{
            Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
            Moedas objMoedas = gson.fromJson(response.body(),Moedas.class);

            return  objMoedas;
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
