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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Record;
import util.DateUtil;

public class Controller {
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
    private Label lblNotice;
    @FXML
    private ImageView imgCover;
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
    Image defaultCover;

    @FXML
    private void initialize() {
        comboBoxCondition.getItems().add("Mint (M)");
        comboBoxCondition.getItems().add("Near Mint (NM or M-)");
        comboBoxCondition.getItems().add("Very Good Plus (VG+)");
        comboBoxCondition.getItems().add("Very Good (VG)");
        comboBoxCondition.getItems().add("Good Plus (G+)");
        comboBoxCondition.getItems().add("Good (G)");
        comboBoxCondition.getItems().add("Fair (F)");
        comboBoxCondition.getItems().add("Poor (P)");
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
            lblArtist.setText(record.getArtist());
            lblRecordTitle.setText(record.getRecordTitle());
            lblReleaseDate.setText(record.getReleaseDate().toString());
            lblGenre.setText(record.getGenre());
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
            lblArtist.setText("");
            lblRecordTitle.setText("");
            lblReleaseDate.setText("");
            lblGenre.setText("");
            lblCondition.setText("");
            lblPrice.setText("");
            lblNotice.setText("");
        }
    }

    //end methode
    public void exit() {
        Platform.exit();
    }

    @FXML
    private void myInfo() {
        // Show the about message.
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Plattensammlung");
        alert.setHeaderText("Persönliche Informationen");
        alert.setContentText("Programmierer: \tBryan Brotschi \nDatum: \t\t\t21.06.2022");
        alert.showAndWait();
    }

    private void getRecord(Record record) {

        if (record != null) {
            txtArtist.setText(record.getArtist());
            txtTitle.setText(record.getRecordTitle());
            txtGenre.setText(record.getGenre());
            txtPrice.setText(record.getPrice().toString());
            txtNotice.setText(record.getNotice());
            txtReleaseDate.setText(DateUtil.format(record.getReleaseDate()));
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
        } else {
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

    private ObservableList<Record> getInitialTableData() {
        List<Record> list = new ArrayList<>();
        list.add(new Record("James Brown", "Reality", LocalDate.of(1974, 9, 29), "Funk", "NM", "Originalpressung US",
                30.50));
        list.add(new Record("Surprize", "Keep On Truckin'", LocalDate.of(1974, 1, 2), "Rock, Blues", "M",
                "Repress, Psychedelic Rock",
                120.50));
        list.add(new Record("Ian Carr with Nucleus", "Labyrinth", LocalDate.of(1973, 1, 2), "Jazz, Rock, Fusion", "VG+",
                "Spaceship label",
                70.45));

        ObservableList<Record> observableList = FXCollections.observableArrayList(list);
        return observableList;
    }

    @FXML
    private void clearRecordDetails() {
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
            errorMessage += "Bitte wählen Sie den Zustand der Platte aus!\n";
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
    private void saveNewRecord() {
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
                showRecordDetails(newRecord);
                tabPane.getSelectionModel().select(tabOverview);
                recordTableView.getItems().add(newRecord);
            }
        }
    }
    @FXML
	private void deleteRecord() {
		int selectedIndex = recordTableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
			confirmationAlert.setTitle("Platte");
			confirmationAlert.setContentText("Löschen?");
			ButtonType okButton = new ButtonType("JA");
			ButtonType noButton = new ButtonType("NEIN");
			confirmationAlert.getButtonTypes().setAll(okButton, noButton);
			Optional<ButtonType> result = confirmationAlert.showAndWait();

				if (result.get() == okButton) {
					System.out.println("Button YES");  //Konsolenausgabe
					System.out.println(selectedIndex);  //Konsolenausgabe
					recordTableView.getItems().remove(selectedIndex);
					clearRecordDetails();
		
				} else if (result.get() == noButton){
					System.out.println("Button NO");  //Konsolenausgabe
					confirmationAlert.close();
				}
		
		} else {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Platte löschen");
			alert.setHeaderText("Keine Platte ausgewählt");
			alert.setContentText("Bitte wählen Sie eine Platte in der Liste aus.");
			alert.showAndWait();

		}

	}
}
