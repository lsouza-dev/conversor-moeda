package principal;

import api.ApiConversor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        boolean applicationRun = true;
        while(applicationRun){
            try {
                ApiConversor api = new ApiConversor();
                Gson gson = new Gson();
                Scanner scanner = new Scanner(System.in);

                System.out.println("""
                    *****************************************************************
                    Seja bem vindo/a ao conversor de moeda do Luiz!!

                    1) Dólar =>> Peso Argentino
                    2) Peso Argentino =>> Dólar
                    3) Dólar =>> Real Brasileiro
                    4) Real Brasileiro =>> Dólar
                    5) Dólar =>> Peso Colombiano
                    6) Peso Colombiano =>> Dólar
                    7) Outras
                    8) Sair
                    
                    Digite uma opção válida:
                    *****************************************************************
                    """);
                int opcao = scanner.nextInt();
                if(opcao < 1 || opcao > 8) throw new InputMismatchException();

                String moedaEscolhida = "";
                String moedaParaConversao = "";

                switch (opcao){
                    case 1:
                        moedaEscolhida = "USD";
                        moedaParaConversao = "ARS";
                        break;
                    case 2:
                        moedaEscolhida = "ARS";
                        moedaParaConversao = "USD";
                        break;
                    case 3:
                        moedaEscolhida = "USD";
                        moedaParaConversao = "BRL";
                        break;
                    case 4:
                        moedaEscolhida = "BRL";
                        moedaParaConversao = "USD";
                        break;
                    case 5:
                        moedaEscolhida = "USD";
                        moedaParaConversao = "COP";
                        break;
                    case 6:
                        moedaEscolhida = "COP";
                        moedaParaConversao = "USD";
                        break;
                    case 7:
                        System.out.println("Digite o código da moeda escolhida:");
                        moedaEscolhida = scanner.next().toUpperCase();
                        System.out.println("Digite o código da moeda para conversão:");
                        moedaParaConversao = scanner.next().toUpperCase();
                        break;
                    case 8:
                        System.out.println("Saindo...");
                        applicationRun = false;
                        break;

                }

                System.out.println("Digite o valor da moeda escolhida: ");
                double valor = scanner.nextDouble();

                api.RequisicaoApi(valor,moedaEscolhida);
                Map<String,Double> moedas = gson.fromJson(new FileReader("moedas.json"),Map.class);

                double valorMoedaOrigem = moedas.get(moedaEscolhida);
                double valorMoedaConversao = moedas.get(moedaParaConversao);
                double valorConvertido = (valor * valorMoedaOrigem) * valorMoedaConversao;
                
                System.out.println(String.format("\n%.2f em %s convertido para %s é: %.2f", valor,moedaEscolhida,moedaParaConversao,valorConvertido));

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }catch (InputMismatchException ex){
                System.out.println("Digite um valor válido e tente novamente.");
            }catch (NullPointerException ex){
                System.out.println(ex.getMessage());
            }
        }

    }
}
