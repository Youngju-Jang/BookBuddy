package moviebuddy.domain;

import moviebuddy.MovieBuddyFactory;
import moviebuddy.MovieBuddyProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

/**
 * @author springrunner.kr@gmail.com
 */
//@ExtendWith(SpringExtension.class) //junit test시
//@ContextConfiguration(classes = MovieBuddyFactory.class)

@ActiveProfiles(MovieBuddyProfile.CSV_MODE)
@SpringJUnitConfig(MovieBuddyFactory.class)
public class MovieFinderTest {


//@Autowired >> 1)생성자, 2)settter 3) field(비추) 사옹가능
	//@Autowired ApplicationContext applicationContext;
	@Autowired MovieFinder movieFinder; // 방법 3

	// 방법 1
//	@Autowired
//	MovieFinderTest(MovieFinder movieFinder){
//		this.movieFinder = movieFinder;
//	}

	// 방법 2
//	@Autowired
//	void setMovieFinder(MovieFinder movieFinder){
//		this.movieFinder = movieFinder;
//	}

	@Test
	void NotEmpty_directedBy(){
		List<Movie> movies =  movieFinder.directedBy("MIchael Bay");
		Assertions.assertEquals(3, movies.size());
	}

	@Test
	void NotEmpty_ReleasedYearBy(){
		List<Movie> movies = movieFinder.releasedYearBy(2015);
		Assertions.assertEquals(225, movies.size());
	}


	
}
