import model.Weather;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class WeatherTest {
    Weather weather;

    @Test
    public void testGets() {
        try {
            weather = new Weather();
        } catch (IOException e) {
            fail();
        }
        weather.getCurrentTemp();
        weather.getDescription();
        weather.getMaxTemp();
        weather.getMinTemp();
        weather.getMainDescription();
    }
}
