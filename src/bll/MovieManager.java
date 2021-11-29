package bll;

import be.Movie;
import bll.util.MovieSearcher;
import dal.IMovieDataAccess;
import dal.db.MovieDAO_DB;

import java.util.List;

public class MovieManager {

    private MovieSearcher movieSearcher = new MovieSearcher();

    private IMovieDataAccess movieDAO;

    public MovieManager() {
        movieDAO = new MovieDAO_DB();
    }

    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    public List<Movie> searchMovies(String query) throws Exception {
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchResult = movieSearcher.search(allMovies, query);
        return searchResult;
    }
    public Movie createMovie(String name, int year) throws Exception {

        Movie m = movieDAO.createMovie(name,year);

        return m;
    }
    public void deleteMovie(Movie movie) throws Exception {
        movieDAO.deleteMovie(movie);
    }
    public void updateMovie(Movie movie) throws Exception
    {
        movieDAO.updateMovie(movie);
    }

}
