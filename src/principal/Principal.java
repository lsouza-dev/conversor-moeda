package principal;

import api.ApiConversor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import logs.Log;
import modelos.DataFormatter;
import modelos.Moedas;
import modelos.MoedasAbstract;
import modelos.ValueFormatter;
import writer.MoedasWriter;

import java.io.*;
import java.util.*;

public class Principal {
    public static void main(String[] args) {
        boolean applicationRun = true;
        ApiConversor api = new ApiConversor();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        Scanner scanner = new Scanner(System.in);
        String fileName = "%s.json";
        String jsonMoedasDisponiveis = "moedasDisponiveis.json";

        var response = api.RequisicaoApi("BRL");
        Moedas moedasDisponiveis = api.GetMoedas(response);
        MoedasWriter writerIncial = new MoedasWriter();
        MoedasWriter writerDinamico = new MoedasWriter();
        writerIncial.Write(moedasDisponiveis,jsonMoedasDisponiveis);
        try{
            MoedasAbstract.setMoedas(gson.fromJson(new FileReader(String.format(writerIncial.getPath(),jsonMoedasDisponiveis)),Map.class));
        }catch (FileNotFoundException ex){
            System.out.println("Não foi encontrado um arquivo com o nome inserido.");
        }
        while (applicationRun) {
            try {
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
                if (opcao < 1 || opcao > 8) throw new InputMismatchException();

                String moedaEscolhida = "";
                String moedaParaConversao = "";


                switch (opcao) {
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
                        MoedasAbstract.ShowKeys();
                        System.out.println("Digite o código da moeda escolhida:");
                        moedaEscolhida = scanner.next().toUpperCase();
                        if(!MoedasAbstract.getMoedas().containsKey(moedaEscolhida)) throw new NoSuchElementException("Não foi possível encontrar a moeda inserida!\nVerifique as moedas disponíveis na lista e tente novamente.");
                        System.out.println("Digite o código da moeda para conversão:");
                        moedaParaConversao = scanner.next().toUpperCase();
                        break;
                    case 8:
                        System.out.println("Saindo...");
                        applicationRun = false;
                        break;

                }
                if (applicationRun) {
                    System.out.println("Digite o valor da moeda escolhida: ");
                    double valor = scanner.nextDouble();


                    var responseDinamica = api.RequisicaoApi(moedaEscolhida);
                    var moedaDinamica = api.GetMoedas(responseDinamica);
                    writerDinamico.Write(moedaDinamica, String.format(fileName,moedaEscolhida));
                    Map<String, Double> moedas = gson.fromJson(new FileReader(String.format(writerDinamico.getPath(), String.format(fileName, moedaEscolhida))), Map.class);

                    try{
                        double valorMoedaOrigem = moedas.get(moedaEscolhida);
                        double valorMoedaConversao = moedas.get(moedaParaConversao);
                        double valorConvertido = (valor / valorMoedaOrigem) * valorMoedaConversao;

                        String data = new DataFormatter().Formatar("dd/MM/yyyy HH:mm:ss");
                        String valorFinalFormatado = new ValueFormatter(moedaParaConversao,valorConvertido).FormatarValor().replace("\u00A0"," ");
                        String valorInicialFormatado = new ValueFormatter(moedaEscolhida,valor).FormatarValor().replace("\u00A0"," ");

                        System.out.println(String.format("\n%s convertido para %s é: %s", valorInicialFormatado, moedaParaConversao,valorFinalFormatado));

                        String conversaoLog = String.format("%s | Conversão realizada: %s = %s", data,valorInicialFormatado,valorFinalFormatado);
                        Log log = new Log();
                        log.CreateLog(conversaoLog);

                    }catch (NullPointerException ex){
                        System.out.println("Código da moeda para conversão inválido.");
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("O Arquivo especificado não foi encontrado.");
            } catch (InputMismatchException ex) {
                System.out.println("Digite um valor válido e tente novamente.");
                scanner.nextLine();
            }catch (NullPointerException | IllegalArgumentException | NoSuchElementException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
