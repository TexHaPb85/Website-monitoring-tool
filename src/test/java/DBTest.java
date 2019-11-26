import com.providesupportLLC.model.MonitoringSource;
import com.providesupportLLC.repositories.SourceRepository;
import com.providesupportLLC.repositories.SourceRepositoryJDBC;
import org.junit.Test;

public class DBTest {
    @Test
    public void updateTest(){
        SourceRepository repo = SourceRepositoryJDBC.getInstance();
        MonitoringSource source = new MonitoringSource("https://www.youtube.com/");
        source.setMinContentLength(100);
        repo.updateSource(source);
    }
}
