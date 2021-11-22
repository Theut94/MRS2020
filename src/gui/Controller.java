package gui;

import be.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    public TextField txtMovieSearch;
    public ListView<Movie> lstMovies;
    public Button crtButton;
    public Button dltButton;
    public Button updButton;
    public TextField nameTxt;
    public TextField yearTxt;

    private MovieModel movieModel;

    public Controller()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }


    public void dltButton(ActionEvent actionEvent) throws Exception {

        movieModel.deleteMovie(lstMovies.getSelectionModel().getSelectedItem());

    }

    public void crtButton(ActionEvent actionEvent) throws Exception {
        String name =nameTxt.getText();
        int year = Integer.parseInt(yearTxt.getText());
        movieModel.createMovie(name,year);
    }

    public void updButton(ActionEvent actionEvent) throws Exception {
        int id = lstMovies.getSelectionModel().getSelectedItem().getId();
        int year = Integer.parseInt(yearTxt.getText());
        String name = nameTxt.getText();
        Movie movie = new Movie(id, year, name);
        movieModel.updateMovie(movie);

    }
}
