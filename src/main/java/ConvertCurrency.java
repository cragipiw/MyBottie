import java.text.SimpleDateFormat;

public class ConvertCurrency{

    public String doConvertValue(int amount, Model model) {
        String format = "dd MMMM yyyy";

        String valueCurrency = String.format("%.2f",amount/(model.getValue()/model.getNominal()));
        String date = new SimpleDateFormat(format).format(model.getDate());

        return "Если обменять ваши рубли вы получите: " + valueCurrency + " " + model.getName() + "\nКурс на : " + date;
    }

}