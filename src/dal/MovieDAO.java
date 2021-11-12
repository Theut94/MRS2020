package dal;

import be.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private List<Movie> allMovies;

    public MovieDAO()
    {
        allMovies = new ArrayList<>();

    }

    public List<Movie> getAllMovies() throws IOException {
        File moviesFile = new File(MOVIES_FILE);


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(moviesFile))) {
            boolean hasLines = true;
            while (hasLines) {
                String line = bufferedReader.readLine();
                hasLines = (line != null);
                if (hasLines && !line.isBlank())
                {
                    String[] separatedLine = line.split(",");

                    int id = Integer.parseInt(separatedLine[0]);
                    int year = Integer.parseInt(separatedLine[1]);
                    String title = separatedLine[2];
                    if(separatedLine.length > 3)
                    {
                        for(int i = 3; i < separatedLine.length; i++)
                        {
                            title += "," + separatedLine[i];
                        }
                    }
                    Movie movie = new Movie(id, year, title);
                    allMovies.add(movie);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void updateMovie(Movie movie) throws Exception {
        for (Movie m : allMovies)
        {
            if(m.getId() == movie.getId())
                m = movie;
        }
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception
    {
        allMovies.remove(movie);
    }

}