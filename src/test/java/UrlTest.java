import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class UrlTest {
    @Test
    public void urlTest(){
        try {
            URL url = new URL("https://sdgdfsgsdfg");
            URLConnection connection = url.openConnection();
            System.out.println(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
