package modelos;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class ValueFormatter {
    private String codMoeda;
    private double valor;

    public ValueFormatter(String codMoeda, double valor) {
        this.codMoeda = codMoeda;
        this.valor = valor;
    }

    public String FormatarValor(){
        Currency currency = Currency.getInstance(codMoeda);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(currency);
        return format.format(valor);
    }
}
