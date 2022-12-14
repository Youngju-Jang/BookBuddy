package moviebuddy.data;

import moviebuddy.ApplicationException;
import moviebuddy.MovieBuddyProfile;
import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieReader;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

// @Component >> @ComponentScan 가 찾을수있게함 // 하위버전 > repository, service ..etc
//@Repository("movieReader")

@Profile(MovieBuddyProfile.CSV_MODE)
@Repository
public class CsvMovieReader extends AbstractMetadataResourceMovieReader implements MovieReader{

    private final CacheManager cacheManager;

    public CsvMovieReader(CacheManager cacheManager){
        this.cacheManager = Objects.requireNonNull(cacheManager);
    }
    /**
     * 영화 메타데이터를 읽어 저장된 영화 목록을 불러온다.
     *
     * @return 불러온 영화 목록
     */
    @Override
    public List<Movie> loadMovies() {
        // cache 저장된데이터 있다면 즉시 반환
        Cache cache = cacheManager.getCache(getClass().getName());
        List<Movie> movies = cache.get("csv.movies", List.class);
        if(Objects.nonNull(movies) && movies.size() >0){
            return movies;
        }
        try {
            final InputStream content = getMetadataResource().getInputStream() ;
            final Function<String, Movie> mapCsv = csv -> {
                try {
                    // split with comma
                    String[] values = csv.split(",");

                    String title = values[0];
                    List<String> genres = Arrays.asList(values[1].split("\\|"));
                    String language = values[2].trim();
                    String country = values[3].trim();
                    int releaseYear = Integer.valueOf(values[4].trim());
                    String director = values[5].trim();
                    List<String> actors = Arrays.asList(values[6].split("\\|"));
                    URL imdbLink = new URL(values[7].trim());
                    String watchedDate = values[8];

                    return Movie.of(title, genres, language, country, releaseYear, director, actors, imdbLink, watchedDate);
                } catch (IOException error) {
                    throw new ApplicationException("mapping csv to object failed.", error);
                }
                // cache 저장된데이터 없다면 획득한 데이터를 캐시에 저장, 반환

            };

            movies = new BufferedReader(new InputStreamReader(content, StandardCharsets.UTF_8))
                    .lines()
                    .skip(1)
                    .map(mapCsv)
                    .collect(Collectors.toList());
        } catch (IOException error) {
            throw new ApplicationException("failed to load movies data.", error);
        }
        cache.put("csv.movies", movies);
        return movies;
    }

}
