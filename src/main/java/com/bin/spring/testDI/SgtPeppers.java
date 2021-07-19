package  com.bin.spring.testDI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype. Component;

@Component(value = "sgtPeppers")
@hot
@Cold
@PropertySource("classpath:Properties/app.properties")
public class SgtPeppers implements CompactDisc {
    private String title = "Sgt.Pepper's Lonely Hearts Club Band";
    private String artist = "The Beatles ";

    // @Value 注解可以从外部属性文件中读取插入到spring bean中，可以采用${}占位符，也可以用#｛｝ EL表达式
    public SgtPeppers(@Value("${name}") String title, @Value("#{T(Math).random()}") String artist) {
        this.title = title;
        this.artist = artist;
    }

    public void play() {
        System.out.println("Playing " + title + " by " + artist);
    }
}

