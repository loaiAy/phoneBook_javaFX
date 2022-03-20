import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class maman14bController {
	@FXML		
    private VBox vbox;

    @FXML
    private ListView<String> List = new ListView<String>();

    @FXML
    private TextField nameField = null;

    @FXML
    private TextField phoneField = null;

    @FXML
    private Button searchBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button removeBtn;
    
    
    public void initialize() {
    	phoneBook.load(List);
    }

    @FXML
    private void addBtnPressed(ActionEvent event) {
    	if(!nameField.getText().isEmpty() && !phoneField.getText().isEmpty()) {
    		String nameString = (String)nameField.getText().trim();
    		String phoneString = (String)phoneField.getText().trim();
    		
    		if(phoneString.length()!=10) {
    			phoneBook.errorInfoBox("The phone number not 10 digits!! , please insert again.", "Input not valid");
    		}
    		else if(nameString.length()<2 &&nameString.length()>20) {
    			phoneBook.errorInfoBox("The name most contain 2-20 letters!! , please insert again.", "Input not valid");
    		}
    		else {
    			Person p = new Person(nameString,phoneString);
    			phoneBook.addToHash(p);
    			phoneBook.updateList(List,nameString,phoneString);
    			nameField.clear();
    			phoneField.clear();
    			addClosingEvent();	
    		}
    	}
    	else {
    		phoneBook.errorInfoBox("missing info!! , please insert again.", "Input not valid");
    	}
    }

    @FXML
    private void removeBtnPressed(ActionEvent event) {
    	int selectedID = List.getSelectionModel().getSelectedIndex();
    	String data = List.getSelectionModel().getSelectedItem();
    	String Key = data.trim();
    	Key = getKey(Key);
        List.getItems().remove(selectedID);
        phoneBook.delete(Key);
        addClosingEvent();
    }
    
    private String getKey(String Key) {
     	char[] ch = Key.toCharArray();
    	int i = 0;
    	int j = 0;
    	for(i=0;i<ch.length;i++) {
    		if(Character.isWhitespace(ch[i])&&Character.isWhitespace(ch[i+1])) {
    			break;
    		}
    	}
   
    	char[] ke = new char[i];
    	while(j<i) {
    		ke[j] = ch[j];
    		j++;
    	}
    	Key = String.valueOf(ke);
		return Key;
	}
    

	@FXML
    private void searchBtnPressed(ActionEvent event) {
    	if(!nameField.getText().isEmpty() && phoneField.getText().isEmpty()) {
    		String nameString = (String)nameField.getText();
    		String phone = phoneBook.searchOnHash(nameString);
    		
    		if(phone!=null) {
    			phoneBook.infoBox("phone number : " + phone, "display number");
    		}
    		else {
    			phoneBook.errorInfoBox("no existing contact with the provided name.", "Input not valid");
    		}
    	}
    	else {
    		phoneBook.errorInfoBox("missing info , plese provide again.", "Input not valid");
    	}
    }

    @FXML
    private void updateBtnPressed(ActionEvent event) {
    	if(!phoneField.getText().isEmpty()) {
    		String s = phoneField.getText().trim();
    		if(s.length()==10) {
	    		int selectedID = List.getSelectionModel().getSelectedIndex();
		    	String data = List.getSelectionModel().getSelectedItem();
		    	String Key = data.trim();
		    	Key = getKey(Key);
		    	Person p = new Person(Key,phoneField.getText());
		    	phoneBook.addToHash(p);
		    	List.getItems().remove(selectedID);
		    	phoneBook.updateList(List, Key, phoneField.getText());
		    	addClosingEvent();
    		}
    		else {
    			phoneBook.errorInfoBox("The phone number must be 10 digits!! , please insert again.", "Input not valid");
    		}
    	}
    	
    	else {
    		phoneBook.errorInfoBox("missing info!! , please insert again.", "Input not valid");
    	}
    }

	private void addClosingEvent() {
		Stage stage = (Stage)((Node) vbox).getScene().getWindow();
		stage.getScene().getWindow().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event1 -> {

			phoneBook.saveToFile();
		});	
	}

}
