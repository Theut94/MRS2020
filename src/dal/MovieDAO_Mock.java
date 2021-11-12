package dal;

import be.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDAO_Mock implements IMovieDataAccess {

    private List<Movie> allMovies;

    public MovieDAO_Mock()
    {
        allMovies = new ArrayList<>();
        allMovies.add(new Movie(1, 2020,"Trump - the movie"));
        allMovies.add(new Movie(1, 2024, "Trump - I did it again"));
        allMovies.add(new Movie(1, 2028,"Trump - The new dictator on the block"));

    }

    @Override
    public List<Movie> getAllMovies() {
        return allMovies;
    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {

        int i = allMovies.size();
        Movie m =  new Movie(i, year, title);
        allMovies.add(m);
        return m;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception
    {



    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
        allMovies.remove(movie);

    }

}
