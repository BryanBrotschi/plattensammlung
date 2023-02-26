package controller;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Record;
import model.Record.Category;
import util.DateUtil;

public class Controller {
    private static final String FILE_PATH = "src/main/resources/records/records.json";
    private static final String strDefaultCover = "src/main/resources/images/defaultrecord.png";
    private static ObservableList<Record> recordList;
    @FXML
    private TableView<Record> recordTableView;
    @FXML
    private TableColumn<Record, String> recordTitleColumn;
    @FXML
    private TableColumn<Record, String> artistColumn;
    @FXML
    private Tab tabEditor;
    @FXML
    private Tab tabOverview;
    @FXML
    private TabPane tabPane;
    @FXML
    private Label lblReleaseDate;
    @FXML
    private Label lblGenre;
    @FXML
    private Label lblRecordTitle;
    @FXML
    private Label lblCondition;
    @FXML
    private Label lblPrice;
    @FXML
    private Label lblArtist;
    @FXML
    private Label lblCategory;
    @FXML
    private Label lblNotice;
    @FXML
    private ImageView imgCover;
    @FXML
    private ImageView imgCoverEditor;
    @FXML
    private TextField txtCategory;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtGenre;
    @FXML
    private TextField txtNotice;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtReleaseDate;
    @FXML
    private TextField txtTitle;
    @FXML
    private ComboBox<String> comboBoxCondition;
    @FXML
    private ComboBox<Category> comboBoxCategory;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnList;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnSave;
    @FXML
    private Label systemLabel;

    Record selectedRecord;
    
    Image image;
    List<Record> list;

    @FXML
    private void initialize() throws IOException {
        comboBoxCondition.getItems().add("Mint (M)");
        comboBoxCondition.getItems().add("Near Mint (NM or M-)");
        comboBoxCondition.getItems().add("Very Good Plus (VG+)");
        comboBoxCondition.getItems().add("Very Good (VG)");
        comboBoxCondition.getItems().add("Good Plus (G+)");
        comboBoxCondition.getItems().add("Good (G)");
        comboBoxCondition.getItems().add("Fair (F)");
        comboBoxCondition.getItems().add("Poor (P)");

        // Enum mit DropDown verbinden
        ObservableList<Category> categoryList = FXCollections.observableArrayList(Category.values());
        comboBoxCategory.setItems(categoryList);

        recordList = getInitialTableData();
        recordTableView.setItems(recordList);
        recordTitleColumn.setCellValueFactory(cellData -> cellData.getValue().recordTitleProperty());
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        showRecordDetails(null);
        recordTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showRecordDetails(newValue));
    }

    // Detailansicht
    private void showRecordDetails(Record record) {
        if (record != null) {
            // comboBoxCategory.setValue(record.getCategory());
            lblCategory.setText(record.getCategory().toString());
            lblArtist.setText(record.getArtist());
            lblRecordTitle.setText(record.getRecordTitle());
            lblReleaseDate.setText(record.getReleaseDate().toString());
            lblGenre.setText(record.getGenre());
            if (record.getCover() != null) {
                image = new Image(record.getCover());
            } else {
                image = new Image(strDefaultCover);
            }
            imgCover.setImage(image);
            switch (record.getCondition()) {
                case "M":
                    lblCondition.setText("Mint (M)");
                    break;
                case "NM":
                    lblCondition.setText("Near Mint (NM)");
                    break;
                case "VG+":
                    lblCondition.setText("Very Good Plus (VG+)");
                    break;
                case "VG":
                    lblCondition.setText("Very Good (VG)");
                    break;
                case "G+":
                    lblCondition.setText("Good Plus (G+)");
                    break;
                case "G":
                    lblCondition.setText("Good (G)");
                    break;
                case "F":
                    lblCondition.setText("Fair (F)");
                    break;
                case "P":
                    lblCondition.setText("Poor (P)");
                    break;
            }
            lblPrice.setText(record.getPrice().toString());
            lblNotice.setText(record.getNotice());
        } else {
            lblCategory.setText("");
            lblArtist.setText("");
            lblRecordTitle.setText("");
            lblReleaseDate.setText("");
            lblGenre.setText("");
            lblCondition.setText("");
            lblPrice.setText("");
            lblNotice.setText("");
        }
    }

    // end methode
    public void exit() {
        Platform.exit();
    }

    @FXML
    private void myInfo() {
        // Show the about message.
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Musiksammlung");
        alert.setHeaderText("Persönliche Informationen");
        alert.setContentText("Programmierer: \tBryan Brotschi & Andreas Moser \nDatum: \t\t\t26.02.2023");
        alert.showAndWait();
    }

    private void getRecord(Record record) {

        if (record != null) {
            // comboBoxCategory.setValue(record.getCategory());

            txtArtist.setText(record.getArtist());
            txtTitle.setText(record.getRecordTitle());
            txtGenre.setText(record.getGenre());
            txtPrice.setText(record.getPrice().toString());
            txtNotice.setText(record.getNotice());
            txtReleaseDate.setText(DateUtil.format(record.getReleaseDate()));
            if (record.getCover() != null) {
                image = new Image(record.getCover());
            } else {
                image = new Image(strDefaultCover);
            }
            imgCoverEditor.setImage(image);
            switch (record.getCondition()) {
                case "M":
                    comboBoxCondition.getSelectionModel().select(0);
                    break;
                case "NM":
                    comboBoxCondition.getSelectionModel().select(1);
                    break;
                case "VG+":
                    comboBoxCondition.getSelectionModel().select(2);
                    break;
                case "VG":
                    comboBoxCondition.getSelectionModel().select(3);
                    break;
                case "G+":
                    comboBoxCondition.getSelectionModel().select(4);
                    break;
                case "G":
                    comboBoxCondition.getSelectionModel().select(5);
                    break;
                case "F":
                    comboBoxCondition.getSelectionModel().select(6);
                    break;
                case "P":
                    comboBoxCondition.getSelectionModel().select(7);
                    break;
            }
            switch (record.getCategory().name()) {
                case "Kasette":
                    comboBoxCategory.getSelectionModel().select(0);
                    break;
                case "CD":
                    comboBoxCategory.getSelectionModel().select(1);
                    break;
                case "VinylRecord":
                    comboBoxCategory.getSelectionModel().select(2);
                    break;
            }
        }
    }

    @FXML
    private void gotoRecordEditor() {
        selectedRecord = recordTableView.getSelectionModel().getSelectedItem();
        getRecord(selectedRecord);
        tabPane.getSelectionModel().select(tabEditor);
    }

    @FXML
    private void gotoRecordOverview() {
        selectedRecord = recordTableView.getSelectionModel().getSelectedItem();
        showRecordDetails(selectedRecord);
        tabPane.getSelectionModel().select(tabOverview);
    }

    private ObservableList<Record> getInitialTableData() throws IOException {
        File f = new File(FILE_PATH);
        if (f.exists() && !f.isDirectory()) {
            list = readRecordsFromJson(FILE_PATH);
        } else {
            createJsonFile();
            list = readRecordsFromJson(FILE_PATH);
        }
        return FXCollections.observableArrayList(list);
    }

    @FXML
    private void clearRecordDetails() {
        // comboBoxCategory.getSelectionModel().clearSelection();
        txtArtist.setText("");
        txtGenre.setText("");
        txtNotice.setText("");
        txtPrice.setText("");
        txtReleaseDate.setText("");
        txtTitle.setText("");
        comboBoxCondition.getSelectionModel().clearSelection();
        recordTableView.getSelectionModel().clearSelection();
        selectedRecord = null;
    }

    private boolean isInputValid() {
        String errorMessage = "";
        // if (comboBoxCategory.getSelectionModel().isEmpty()) {
        // errorMessage += "Bitte wählen Sie die Kategorie aus!\n";
        // }
        if (txtArtist.getText().isEmpty()) {
            errorMessage += "Artist ist leer!\n";
        }
        if (txtTitle.getText() == null || txtTitle.getText().length() == 0) {
            errorMessage += "Titel ist ungültig oder fehlt!\n";
        }
        if (txtReleaseDate.getText() == null || txtReleaseDate.getText().length() == 0) {
            errorMessage += "Releasedate fehlt.\n";
        } else {
            if (!DateUtil.validDate(txtReleaseDate.getText())) {
                errorMessage += "Datumsformat ist nicht richtig. \n Format: dd.mm.yyyy!\n";
            }
        }
        if (txtGenre.getText() == null || txtGenre.getText().length() == 0) {
            errorMessage += "Genre fehlt";
        }
        if (txtPrice.getText() == null || txtPrice.getText().length() == 0) {
            errorMessage += "Preis fehlt!\n";
        } else {
            try {
                Double.parseDouble(txtPrice.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Keine gültige Preisangabe!\n";
            }
        }
        if (comboBoxCondition.getSelectionModel().isEmpty()) {
            errorMessage += "Bitte wählen Sie den Zustand aus!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Felder Valdierung");
            alert.setHeaderText("Bitte korrigieren Sie die ungültigen Felder.");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
        return false;
    }

    @FXML
    private void uploadCover() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Cover Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        // Show the file chooser and wait for the user to select a file
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Load the selected image file into an Image object
            Image image = new Image(selectedFile.toURI().toString());
            // imgCoverEditor = new ImageView();
            // Assign the Image object to an ImageView in SceneBuilder
            imgCover.setImage(image);
            imgCoverEditor.setImage(image);
            selectedRecord.setCover(selectedFile.toURI().toString());
        }
    }

    @FXML
    private void saveNewRecord() throws IOException {
        if (isInputValid()) {
            Record selectedRecord = recordTableView.getSelectionModel().getSelectedItem();
            if (selectedRecord != null) {
                selectedRecord.setArtist(txtArtist.getText());
                selectedRecord.setGenre(txtGenre.getText());
                selectedRecord.setReleaseDate(DateUtil.parse(txtReleaseDate.getText()));
                selectedRecord.setRecordTitle(txtTitle.getText());
                selectedRecord.setPrice(Double.valueOf(txtPrice.getText()));
                selectedRecord.setNotice(txtNotice.getText());
                switch (comboBoxCondition.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        selectedRecord.setCondition("M");
                        break;
                    case 1:
                        selectedRecord.setCondition("NM");
                        break;
                    case 2:
                        selectedRecord.setCondition("VG+");
                        break;
                    case 3:
                        selectedRecord.setCondition("VG");
                        break;
                    case 4:
                        selectedRecord.setCondition("G+");
                        break;
                    case 5:
                        selectedRecord.setCondition("G");
                        break;
                    case 6:
                        selectedRecord.setCondition("F");
                        break;
                    case 7:
                        selectedRecord.setCondition("P");
                        break;
                }
                switch (comboBoxCategory.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        selectedRecord.setCategory(Category.Kasette);
                        break;
                    case 1:
                        selectedRecord.setCategory(Category.CD);
                        break;
                    case 2:
                        selectedRecord.setCategory(Category.VinylRecord);
                        break;
                }
                list.remove(selectedRecord);
                list.add(selectedRecord);
                saveRecordsToJson((ArrayList<Record>) list, FILE_PATH);
                showRecordDetails(selectedRecord);
                tabPane.getSelectionModel().select(tabOverview);

            } else {
                Record newRecord = new Record();
                newRecord.setArtist(txtArtist.getText());
                newRecord.setGenre(txtGenre.getText());
                newRecord.setReleaseDate(DateUtil.parse(txtReleaseDate.getText()));
                newRecord.setRecordTitle(txtTitle.getText());
                newRecord.setPrice(Double.valueOf(txtPrice.getText()));
                newRecord.setNotice(txtNotice.getText());
                switch (comboBoxCondition.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        newRecord.setCondition("M");
                        break;
                    case 1:
                        newRecord.setCondition("NM");
                        break;
                    case 2:
                        newRecord.setCondition("VG+");
                        break;
                    case 3:
                        newRecord.setCondition("VG");
                        break;
                    case 4:
                        newRecord.setCondition("G+");
                        break;
                    case 5:
                        newRecord.setCondition("G");
                        break;
                    case 6:
                        newRecord.setCondition("F");
                        break;
                    case 7:
                        newRecord.setCondition("P");
                        break;
                }
                switch (comboBoxCategory.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        newRecord.setCategory(Category.Kasette);
                        break;
                    case 1:
                        newRecord.setCategory(Category.CD);
                        break;
                    case 2:
                        newRecord.setCategory(Category.VinylRecord);
                        break;
                }
                list.add(newRecord);
                saveRecordsToJson((ArrayList<Record>) list, FILE_PATH);
                showRecordDetails(newRecord);
                tabPane.getSelectionModel().select(tabOverview);
                recordTableView.getItems().add(newRecord);
            }
        }
    }

    @FXML
    private void deleteRecord() throws IOException {
        int selectedIndex = recordTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Medium");
            confirmationAlert.setContentText("Löschen?");
            ButtonType okButton = new ButtonType("JA");
            ButtonType noButton = new ButtonType("NEIN");
            confirmationAlert.getButtonTypes().setAll(okButton, noButton);
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.get() == okButton) {
                System.out.println("Button YES"); // Konsolenausgabe
                System.out.println(selectedIndex); // Konsolenausgabe
                recordTableView.getItems().remove(selectedIndex);
                list.remove(selectedIndex);
                saveRecordsToJson((ArrayList<Record>) list, FILE_PATH);
                clearRecordDetails();

            } else if (result.get() == noButton) {
                System.out.println("Button NO"); // Konsolenausgabe
                confirmationAlert.close();
            }

        } else {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Medium löschen");
            alert.setHeaderText("Kein Medium ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Medium in der Liste aus.");
            alert.showAndWait();

        }
    }

    private static void createJsonFile() throws IOException {
        ArrayList<Record> records = new ArrayList<>();
        Record record = new Record(Category.VinylRecord, "John Doe", "My Record Title", LocalDate.parse("1995-09-29"),
                "Pop", "Good", "None", 12.99, "src/main/resources/images/defaultrecord.png");

        records.add(record);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // register the JavaTimeModule
        mapper.writeValue(new File(FILE_PATH), records);
        System.out.println("JSON file created successfully.");
    }

    private static ArrayList<Record> readRecordsFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File file = new File(filePath);
        ArrayList<Record> records = objectMapper.readValue(file, new TypeReference<ArrayList<Record>>() {
        });
        return records;
    }

    private static void saveRecordsToJson(ArrayList<Record> records, String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writeValue(new File(filePath), records);
    }
}
