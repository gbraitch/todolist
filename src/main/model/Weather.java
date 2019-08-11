package model;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Weather {
    private double currentTemp;
    private double maxTemp;
    private double minTemp;
    private String mainDescription;
    private String description;
    private JSONObject parsedJson;

    public Weather() throws IOException {

        BufferedReader br = null;
        StringBuilder sb;

        try {
            String apikey = "0413388699f7c8d38d735643cdaa8dc3";
            String cityWeatherQuery = "http://api.openweathermap.org/data/2.5/weather?q=Vancouver,ca&units=metric&APPID=";
            String theURL = cityWeatherQuery + apikey;
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

        } finally {
            if (br != null) {
                br.close();
            }
        }

        parsedJson = new JSONObject(sb.toString());

        getWeatherData();
        getTemperatureData();

    }

    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    private void getWeatherData() {
        JSONObject weatherObj = (JSONObject)parsedJson.getJSONArray("weather").get(0);
        description = weatherObj.getString("description");
        description = description.toUpperCase();
        mainDescription = weatherObj.getString("main");
    }

    private void getTemperatureData() {
        JSONObject mainObj = parsedJson.getJSONObject("main");
        currentTemp = round((mainObj.getDouble("temp")),1);
        minTemp = round((mainObj.getDouble("temp_min")),1);
        maxTemp = round((mainObj.getDouble("temp_max")),1);
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public String getDescription() {
        return description;
    }

    public String getMainDescription() {
        return mainDescription;
    }


}
