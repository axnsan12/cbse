package cbse.test.movie;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonMovieFinder implements MovieFinder {
    private final String fileName;

    public JsonMovieFinder(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Movie> findAll() {
        try {
            JsonReader json = new JsonReader(new FileReader(fileName));
            return new Gson().fromJson(json, new TypeToken<ArrayList<Movie>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
