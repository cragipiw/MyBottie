import java.text.SimpleDateFormat;

public class ConvertCurrency{

    public String doConvertValue(String amount, Model model) {
        String format = "dd MMMM yyyy";
        int currencyAmount = Integer.parseInt(amount);

        String valueCurrency = String.format("%.2f",currencyAmount/(model.getValue()/model.getNominal()));
        String date = new SimpleDateFormat(format).format(model.getDate());

        return "Если обменять ваши рубли вы получите: " + valueCurrency + " " + model.getName() + "\nКурс на : " + date;
    }

}