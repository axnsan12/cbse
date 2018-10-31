package cbse.test.movie;

import cbse.ioc.CbseIoc;
import cbse.ioc.InjectorConfig;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    private static InjectorConfig jsonConfig() {
        return new InjectorConfig()
                .registerImpl(MovieLister.class)
                .registerImpl(MovieFinder.class, JsonMovieFinder.class, new Object[]{"movies.json"});
    }

    private static InjectorConfig hardcodedConfig() {
        return new InjectorConfig()
                .registerImpl(MovieLister.class)
                .registerImpl(MovieFinder.class, HardcodedMovieFinder.class);
    }

    private static InjectorConfig jsonConfigFromJson() throws FileNotFoundException, ClassNotFoundException {
        return InjectorConfig.fromJson("movie-di-config.json");
    }

    private static void testIoc(InjectorConfig config) {
        CbseIoc ioc = new CbseIoc(config);
        MovieLister lister = ioc.getInstance(MovieLister.class);
        List<Movie> movies = lister.moviesDirectedBy("director2");
        System.out.println(movies);
    }

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
        testIoc(jsonConfig());
        testIoc(hardcodedConfig());
        testIoc(jsonConfigFromJson());
    }
}
