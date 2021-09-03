import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class GettingJSON {
    public String getJSON(URL url) throws IOException {
        Scanner response = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (response.hasNext()) {
            result.append(response.nextLine());
        }
        return result.toString();
    }
}
