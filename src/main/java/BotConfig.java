import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {

    @Bean
    public ConvertCurrency getConvertCurrency(){
        return new ConvertCurrency();
    }

    @Bean
    public ModelFiller getModelFiller(){
        return new ModelFiller(getModel());
    }

    @Bean
    public GettingJSON getGettingJSON(){
        return new GettingJSON();
    }

    @Bean
    public Model getModel(){
        return new Model();
    }
}
