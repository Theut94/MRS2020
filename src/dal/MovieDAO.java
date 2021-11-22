package dal;

import be.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private List<Movie> allMovies;

    public MovieDAO() {
        allMovies = new ArrayList<>();

    }

    public static void main(String[] args) throws Exception {
        MovieDAO movieDAO = new MovieDAO();
        movieDAO.getAllMovies();
        Movie movie = new Movie(17774, 2015, "Django Unchained");
        movieDAO.deleteMovie(movie);
    }

    public List<Movie> getAllMovies() throws IOException {
        File moviesFile = new File(MOVIES_FILE);


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(moviesFile))) {
            boolean hasLines = true;
            while (hasLines) {
                String line = bufferedReader.readLine();
                hasLines = (line != null);
                if (hasLines && !line.isBlank()) {
                    String[] separatedLine = line.split(",");

                    int id = Integer.parseInt(separatedLine[0]);
                    int year = Integer.parseInt(separatedLine[1]);
                    String title = separatedLine[2];
                    if (separatedLine.length > 3) {
                        for (int i = 3; i < separatedLine.length; i++) {
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
        try (FileWriter writer = new FileWriter(MOVIES_FILE, true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            int i = allMovies.get(allMovies.size() - 1).getId() + 1;
            Movie m = new Movie(i, year, title);
            bw.append(i + "," + year + "," + title);
            bw.newLine();
            allMovies.add(m);
            return m;
        }
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

        File moviesFile = new File(MOVIES_FILE);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(moviesFile))){

        for(Movie m : getAllMovies())
            if(movie.getId() == m.getId())
            {
                bw.write(movie.getId() + "," + movie.getYear() + "," + movie.getTitle());
                bw.newLine();
            }
            else
            {
                bw.write(m.getId() + "," + m.getYear() + "," + m.getTitle());
                bw.newLine();
            }}


    }

    @Override
    public void deleteMovie(Movie movie) throws Exception
    {
        File moviesFile = new File(MOVIES_FILE);
       try (BufferedWriter bw = new BufferedWriter(new FileWriter(moviesFile)))
       {
            for (Movie m : getAllMovies()) {
                if (m.getId() == movie.getId())
                    System.out.println("this happened");

                else {
                    bw.write(m.getId() + "," + m.getYear() + "," + m.getTitle());
                    bw.newLine();
                }
            }
       }
    }
}

