package util;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Saveable {
    private PrintWriter writer;

    public Saveable(String outputFileName) throws IOException {
        this.writer = new PrintWriter(outputFileName, "UTF-8");
    }

    public void save(List<String> lines){
        for(String line : lines) {
            writer.println(line);
        }
        writer.close();
    }
}
