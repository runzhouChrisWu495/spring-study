import com.bin.config.MyConfig;
import com.bin.entity.User;
import com.bin.entity.UserFactoryBean;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestUserFactoryBean {
    @Test
    public void test1() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("=======");
        Object bean = context.getBean("userFactoryBean");
        System.out.println(bean.getClass());
        UserFactoryBean factoryBean = new UserFactoryBean();
        User user = factoryBean.getObject();
        System.out.println(user);

        context.close();
    }
}
