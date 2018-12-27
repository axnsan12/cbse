package cbse.osgi.movies.finder.json;

import cbse.osgi.movies.Movie;
import cbse.osgi.movies.MovieFinder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

@Component
@Provides
@Instantiate
public class JsonMovieFinder implements MovieFinder {
    private final Callable<InputStream> jsonInputFactory;

    public JsonMovieFinder() {
        this(Objects.requireNonNull(JsonMovieFinder.class.getClassLoader().getResource("movies.json")));
    }

    public JsonMovieFinder(InputStream jsonInput) {
        jsonInputFactory = () -> jsonInput;
    }

    public JsonMovieFinder(URL url) {
        jsonInputFactory = url::openStream;
    }

    @Override
    public List<Movie> findAll() {
        try {
            JsonReader json = new JsonReader(new InputStreamReader(jsonInputFactory.call()));
            return new Gson().fromJson(json, new TypeToken<ArrayList<Movie>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
