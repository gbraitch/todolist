import model.Weather;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherTest {
    Weather weather;

    @Test
    public void testGets() {
        try {
            weather = new Weather();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(weather.getCurrentTemp() > 0);
        assertFalse(weather.getDescription().isEmpty());
        assertTrue(weather.getMaxTemp() > 0);
        assertTrue(weather.getMinTemp() > 0);
        assertFalse(weather.getMainDescription().isEmpty());
    }
}
