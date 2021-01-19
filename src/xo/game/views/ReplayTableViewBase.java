package xo.game.views;

import com.sun.glass.ui.View;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import xo.game.SinglePlayerController;
import xo.game.XOGame;
import xo.game.dataTypes.GamesHolder;

public class ReplayTableViewBase extends AnchorPane {

    protected final TableView gamesTable;
    protected final TableColumn idColumn;
    protected final TableColumn gameTypeColumn;
    protected final Button backButton;
    private static ReplayTableViewBase gamesTableView;
    private Stage primaryStage;

    public ReplayTableViewBase() {

        gamesTable = new TableView();
        idColumn = new TableColumn();
        gameTypeColumn = new TableColumn();
        backButton = new Button();

        setId("AnchorPane");
        setPrefHeight(600.0);
        setPrefWidth(600.0);

        gamesTable.setPrefHeight(552.0);
        gamesTable.setPrefWidth(600.0);

        idColumn.setPrefWidth(75.0);
        idColumn.setText("GameID");

        gameTypeColumn.setPrefWidth(75.0);
        gameTypeColumn.setText("GameType");

        backButton.setLayoutX(14.0);
        backButton.setLayoutY(561.0);
        backButton.setMnemonicParsing(false);
        backButton.setPrefHeight(25.0);
        backButton.setPrefWidth(81.0);
        backButton.setText("Back");

        backButton.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(XOGame.mainMenuScene);
        });
        gamesTable.getColumns().add(idColumn);
        gamesTable.getColumns().add(gameTypeColumn);
        getChildren().add(gamesTable);
        getChildren().add(backButton);

        gamesTable.setRowFactory(tv -> {
            TableRow<GamesHolder> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    GamesHolder clickedRow = row.getItem();
                    System.out.println(clickedRow.getGameID());
                    //i want to kill myself again
                }
            });
            return row;
        });
    }

    public void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void populateTable(GamesHolder[] games) {
//I want to kill myself right now
        idColumn.setCellValueFactory(new PropertyValueFactory<>("gameID"));

        gameTypeColumn.setCellValueFactory(new PropertyValueFactory<>("gameType"));

        for (GamesHolder game : games) {
            gamesTable.getItems().add(game);

        }

    }

    public static ReplayTableViewBase getInstance() {
        if (gamesTableView == null) {
            synchronized (ReplayTableViewBase.class) {
                if (gamesTableView == null) {
                    gamesTableView = new ReplayTableViewBase();
                }
            }
        }
        return gamesTableView;
    }
}
