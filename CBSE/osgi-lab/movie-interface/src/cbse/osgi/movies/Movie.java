package cbse.osgi.movies;

public class Movie {
    private final String title;
    private final String director;

    @SuppressWarnings("unused")
    private Movie() {
        // for Gson
        this("<gson>", "<gson>");
    }

    public Movie(String title, String director) {
        this.title = title;
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", director='" + director + '\'' +
                '}';
    }
}
