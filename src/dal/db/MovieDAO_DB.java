package dal.db;

import be.Movie;
import dal.IMovieDataAccess;
import dal.MovieDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

    private MyDatabaseConnector databaseConnector;
    private IMovieDataAccess movieDAO ;

    public MovieDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
        movieDAO = new MovieDAO();
    }

    public List<Movie> getAllMovies() throws SQLException {
        ArrayList<Movie> allMovies = new ArrayList<Movie>();
        try(Connection connection = databaseConnector.getConnection())
        {
            String sql = "SELECT * FROM Movie;";

            Statement statement = connection.createStatement();
            if(statement.execute(sql))
            {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next())
                {
                    int id = resultSet.getInt("Id");
                    String title = resultSet.getString("Title");
                    int year = resultSet.getInt("YEAR");
                    Movie movie = new Movie(id,year,title);
                    allMovies.add(movie);

                }
            }

        }
        return allMovies;
    }

    public void fillMovDatabase() throws Exception {
        List<Movie> allMovies = movieDAO.getAllMovies();
        try (Connection con = databaseConnector.getConnection()) {
            String sql = "INSERT INTO dbo.Movie VALUES (?,?);";
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for (Movie mov : allMovies) {
                    statement.setInt(2, mov.getYear());
                    statement.setString(1, mov.getTitle());
                    statement.execute();
                }
            }
        }

    public static void main(String[] args) throws Exception {
        MovieDAO_DB movieDAO_db = new MovieDAO_DB();


    }

    public Movie createMovie(String title, int year) throws Exception {
        int id = -1;
        String sql = "INSERT INTO dbo.Movie VALUES (?,?);";
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(2, year);
            statement.setString(1, title);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = (int) generatedKeys.getLong(1);
                } else {
                    throw new SQLException("No id Obtained");
                }

            }
            if (id != 1)
                return new Movie(id, year, title);

            else return null;
        }

    }


    public void updateMovie(Movie movie) throws Exception {
        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public void deleteMovie(Movie movie) throws Exception {
        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public List<Movie> searchMovies(String query) throws Exception {
        ArrayList<Movie> listofMovies = new ArrayList<Movie>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM [MRS2021MT].[dbo].[Movie] WHERE Title LIKE '%" + query + "';";
            Statement statement = connection.createStatement();
            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("Id");
                    String title = resultSet.getString("Title");
                    int year = resultSet.getInt("YEAR");
                    Movie movie = new Movie(id, year, title);
                    listofMovies.add(movie);

                }
                //TODO Do this
                throw new UnsupportedOperationException();
            }

        }
        return listofMovies;
    }
}