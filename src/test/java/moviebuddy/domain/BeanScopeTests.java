package moviebuddy.domain;

import moviebuddy.MovieBuddyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanScopeTests {

    //싱글톤 스코프 확인
    @Test
    void Equals_MovieFinderBean(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MovieBuddyFactory.class);
        MovieFinder movieFinder = applicationContext.getBean(MovieFinder.class);

        Assertions.assertEquals(movieFinder, applicationContext.getBean(MovieFinder.class));
    }
}
