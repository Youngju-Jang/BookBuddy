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
@Import({MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class})
public class MovieBuddyFactory {
    @Configuration
    static class DomainModuleConfig {
        //@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // 프로토타입 >> 빈객체 요청시마다 새로운 빈객체 생성
        @Bean
        public MovieFinder movieFinder(MovieReader movieReader) {
            return new MovieFinder(movieReader);
        }
    }

    @Configuration
    static class DataSourceModuleConfig {
        @Bean
        public MovieReader movieReader(){
            return new CsvMovieReader();
        }
    }
}