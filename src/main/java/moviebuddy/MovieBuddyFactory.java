package moviebuddy;

import moviebuddy.domain.CsvMovieReader;
import moviebuddy.domain.MovieFinder;
import moviebuddy.domain.MovieReader;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

// Import >> 다른 클래스에서 빈구성정보를 불러오기위해 사용함
//@ImportResource("xml file location") // >> 빈 구성정보 불러올 수 있음
@Configuration
@ComponentScan(basePackages = {"moviebuddy"}) // 자동으로 csvMovieReader찾게할거임
@Import({MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class})
public class MovieBuddyFactory {
    @Configuration
    static class DomainModuleConfig {
    }

    @Configuration
    static class DataSourceModuleConfig {
    }
}
