import com.bin.spring.testAOP.AOPConfig;
import com.bin.spring.testAOP.Actress;
import com.bin.spring.testAOP.Performance;
import com.bin.spring.testDI.CDPlayerConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= AOPConfig.class)
public class AOPTest {
    @Autowired
    Performance actress;

    @Test
    public void test(){
        actress.perform();
        System.out.println("--------------");
        actress.rest(5);

    }
}
