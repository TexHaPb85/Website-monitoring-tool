import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesTest {
    private Properties properties;


    public PropertiesTest() throws IOException {
        this.properties =  new Properties();
        InputStream inputStream = PropertiesTest.class.getClassLoader().getResourceAsStream("application.properties");
        properties.load(inputStream);
    }

    @Test
    public void getTest() {
        System.out.println(properties.getProperty("main_url"));
    }
}
