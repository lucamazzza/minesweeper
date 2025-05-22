package ch.supsi.minesweeper.view;

import ch.supsi.minesweeper.controller.EventHandler;
import ch.supsi.minesweeper.model.AbstractModel;
import ch.supsi.minesweeper.model.GameEventHandler;
import ch.supsi.minesweeper.model.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarViewFxml implements ControlledFxView {

    private static MenuBarViewFxml self;

    private GameEventHandler gameEventHandler;
    private GameModel gameModel;
    private ResourceBundle bundle;


    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private Menu editMenu;
    @FXML
    private Menu helpMenu;
    @FXML
    private MenuItem newMenuItem;
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem saveAsMenuItem;
    @FXML
    private MenuItem quitMenuItem;
    @FXML
    private MenuItem preferencesMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem helpMenuItem;

    private MenuBarViewFxml() {
    }

    public static MenuBarViewFxml getInstance(ResourceBundle bundle) {
        if (self == null) {
            self = new MenuBarViewFxml();
            try {
                URL fxmlUrl = MenuBarViewFxml.class.getResource("/menubar.fxml");
                if (fxmlUrl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl, bundle);
                    fxmlLoader.setController(self);
                    fxmlLoader.load();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return self;
    }

    @Override
    public void initialize(EventHandler eventHandler, AbstractModel model) {
        this.gameEventHandler = (GameEventHandler) eventHandler;
        this.gameModel = (GameModel) model;
        // carica il bundle da qualche altra parte (ad esempio da un campo o metodo)
        // oppure fai un setter separato per il bundle
        this.createBehaviour();
    }

    public void setResourceBundle(ResourceBundle bundle) {
        this.bundle = bundle;

        fileMenu.setText(bundle.getString("menu_file"));
        editMenu.setText(bundle.getString("menu_edit"));
        helpMenu.setText(bundle.getString("menu_help"));

        newMenuItem.setText(bundle.getString("menu_new"));
        openMenuItem.setText(bundle.getString("menu_open"));
        saveMenuItem.setText(bundle.getString("menu_save"));
        saveAsMenuItem.setText(bundle.getString("menu_save_as"));
        quitMenuItem.setText(bundle.getString("menu_quit"));

        preferencesMenuItem.setText(bundle.getString("menu_preferences"));

        aboutMenuItem.setText(bundle.getString("menu_about"));
        helpMenuItem.setText(bundle.getString("menu_help"));
    }



    private void createBehaviour() {
        // new
        this.newMenuItem.setOnAction(event -> this.gameEventHandler.newGame());
        // save
        this.saveMenuItem.setOnAction(event -> this.gameEventHandler.save());
        // add event handlers for all necessary menu items
        // ...
    }

    @Override
    public Node getNode() {
        return this.menuBar;
    }

    @Override
    public void update() {
        // get your data from the model, if needed
        // then update this view here
        System.out.println(this.getClass().getSimpleName() + " updated..." + System.currentTimeMillis());
    }

    @Override
    public void enable() {
        System.out.println("MenuBarView Enabled");
    }

    @Override
    public void disable() {
        System.out.println("MenuBarView Disabled");
    }
}
