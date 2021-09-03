import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ModelFiller {
    private Model model;
    public ModelFiller(Model model) {
        this.model = model;
    }

    public void fillModel(String convertValue) throws IOException, ParseException {
        JSONObject jsonObject = new JSONObject(new GettingJSON().getJSON(new URL("https://www.cbr-xml-daily.ru/daily_json.js")));
        String stringDate = jsonObject.getString("Date");
        model.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(stringDate.substring(0, stringDate.indexOf("T"))));

        JSONObject valute = jsonObject.getJSONObject("Valute");
        JSONObject currencyNeeded = valute.getJSONObject(convertValue);
        model.setNominal(currencyNeeded.getInt("Nominal"));
        model.setName(currencyNeeded.getString("Name"));
        model.setValue(currencyNeeded.getDouble("Value"));
    }
}
