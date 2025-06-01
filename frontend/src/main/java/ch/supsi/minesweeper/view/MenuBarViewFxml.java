package ch.supsi.minesweeper.view;

import ch.supsi.minesweeper.Main;
import ch.supsi.minesweeper.model.EventHandler;
import ch.supsi.minesweeper.model.AbstractModel;
import ch.supsi.minesweeper.model.GameEventHandler;
import ch.supsi.minesweeper.model.GameModel;
import ch.supsi.minesweeper.service.UserPreferences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static ch.supsi.minesweeper.model.Constant.CONFIG_PATH;

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

    private String getManifestAttribute(String attributeName) {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF")) {
            if (stream != null) {
                Manifest manifest = new Manifest(stream);
                Attributes attr = manifest.getMainAttributes();
                return attr.getValue(attributeName);
            }
            return "";
        } catch (Exception e) {
           return "";
        }
    }

    private void createBehaviour() {
        // new
        this.newMenuItem.setOnAction(event -> this.gameEventHandler.newGame());
        // save
        this.saveMenuItem.setOnAction(event -> this.gameEventHandler.save());
        // save as
        this.saveAsMenuItem.setOnAction(evet -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save as...");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV File", "*.csv"));
            File file = fileChooser.showSaveDialog(null);
            this.gameEventHandler.saveAs(file);
        });
        // open
        this.openMenuItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a CSV file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showOpenDialog(null);
            this.gameEventHandler.open(file);
        });
        // preferences
        this.preferencesMenuItem.setOnAction(event -> UserPreferences.getInstance().openPreferencesFile());
        // quit
        this.quitMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(bundle.getString("quit"));
            alert.setHeaderText(bundle.getString("sure_quit"));
            String content = bundle.getString("unsaved_are_lost");
            alert.setContentText(content);
            ButtonType okButton = new ButtonType("Ok");
            ButtonType cancelButton = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(okButton, cancelButton);
            alert.setOnShown(e -> {
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.setAlwaysOnTop(true);
            });
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                this.gameEventHandler.quit();
            }
        });
        // about
        this.aboutMenuItem.setOnAction(event -> {
            Package pkg = Main.class.getPackage();
            String version = pkg.getImplementationVersion();
            if (version == null) version = "1.0.0";
            String build = getManifestAttribute("Build-Time");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setGraphic(null);
            alert.setHeaderText(null);
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/icon.png")));
            ImageView iconView = new ImageView(icon);
            iconView.setFitWidth(64);
            iconView.setFitHeight(64);
            Label appName = new Label("Minesweeper");
            appName.setStyle("-fx-font-size: 18px; -fx-font-family: 'JetBrains Mono'; -fx-font-weight: bold;");
            Label authors = new Label("by Luca Mazza, Roeld Hoxha & Vasco Silva Pereira");
            authors.setStyle("-fx-font-size: 14px; -fx-font-family: 'JetBrains Mono';");
            Label copyright = new Label("2025 SUPSI-DTI. SwEng-1");
            copyright.setStyle("-fx-font-size: 12px; -fx-font-family: 'JetBrains Mono';");
            Label versionInfo = new Label(version + " build: " + build );
            versionInfo.setStyle("-fx-font-size: 10px; -fx-font-family: 'JetBrains Mono';");
            VBox content = new VBox(10, iconView, appName, authors, copyright, versionInfo);
            content.setStyle("-fx-alignment: center; -fx-padding: 20;");
            alert.getDialogPane().setContent(content);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(icon);
            alertStage.setAlwaysOnTop(true);
            alert.showAndWait();
        });
        // help
        this.helpMenuItem.setOnAction(event -> {
            String path = bundle.getString("help_file");
            System.out.println(path);
            try (InputStream in = getClass().getResourceAsStream(path)) {
                if (in == null) {
                    System.out.println("Help file not found: " + path);
                    return;
                }
                File tempFile = File.createTempFile("help", ".pdf");
                tempFile.deleteOnExit();
                try (OutputStream out = new FileOutputStream(tempFile)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(tempFile);
                }
            } catch (IOException e) {
                System.out.println("Cannot open help file");
            }
        });
    }

    @Override
    public Node getNode() {
        return this.menuBar;
    }

    @Override
    public void update() {
        System.out.println(this.getClass().getSimpleName() + " updated..." + System.currentTimeMillis());
    }

    @Override
    public void enable() {
        this.saveMenuItem.setDisable(false);
        this.saveAsMenuItem.setDisable(false);
        System.out.println("MenuBarView Enabled");
    }

    @Override
    public void disable() {
        this.saveMenuItem.setDisable(true);
        this.saveAsMenuItem.setDisable(true);
        System.out.println("MenuBarView Disabled");
    }
}
