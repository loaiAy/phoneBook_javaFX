import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.scene.control.ListView;

public class phoneBook {
	
	private static HashMap<String,String> h = new HashMap<String,String>();
	private static HashMap<String,String> h1 = new HashMap<String,String>();
	public static void initiate() {
		Person p1 = new Person("loai abu yosef" , "0525655466");
		Person p2 = new Person("program abu yosef" , "0525555555");
		Person p3 = new Person("java abu yosef" , "0521111111");
		Person p4 = new Person("ameer java" , "0525777777");
		Person p5 = new Person("computer","1234567890");
		Person p6 = new Person("science","0987654321");
		Person p7 = new Person("puter scie","1111111111");
		addToHash(p1);
		addToHash(p2);
		addToHash(p3);
		addToHash(p4);
		addToHash(p5);
		addToHash(p6);
		addToHash(p7);
	}
	
	public static void loadFromFile() {
		File file = getFile();
		if (file != null) {
			try {
				FileInputStream fi = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fi);
				h1 = (HashMap<String, String>)ois.readObject();
				ois.close();
				fi.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveToFile() {
		File file = getFile();
		try {
			FileOutputStream fo = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fo);
			out.writeObject(h1);
			out.close();
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static File getFile() {
		FileChooser fileChooser = new FileChooser(); 
		fileChooser.setTitle("select a file"); 
		fileChooser.setInitialDirectory(new File(".")); 
		return fileChooser.showOpenDialog(null);
	}

	public static void addToHash(Person p) {
		h1.put(p.getFullName(), p.getPhoneNumber());
	}

	public static void load(ListView<String> list) {
		list.getItems().clear();
		loadFromFile();
		for (Map.Entry mapElement : h1.entrySet()) {
			String s = (String) mapElement.getKey();
			String space = getSpace(s);
			list.getItems().add("\t\t" + (String)mapElement.getKey() + space + "\t\t\t\t\t" + (String)mapElement.getValue());
		}
		list.getItems().sort(null);
	}

	public static void delete(String key) {
		String s;
		for (Map.Entry mapElement : h1.entrySet()) {
			s = (String) mapElement.getKey();
			if(s.contentEquals(key)) {
				h1.remove(s);
				break;
			}
		}
	}

	public static String searchOnHash(String nameString) {
		if(h1.containsKey(nameString)) {
			return (String)h1.get(nameString);
		}
		return null;
	}

	public static void infoBox(String infoMessage, String titleBar){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titleBar);
		alert.setContentText(infoMessage);
		alert.showAndWait();
   	 }

    public static void errorInfoBox(String infoMessage , String headerMessage) {
    	Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setHeaderText(headerMessage);
		errorAlert.setContentText(infoMessage);
		errorAlert.showAndWait();
	}

	public static Object getHhash() {
		return h;
	}

	
	public static void updateList(ListView<String> list, String nameString, String phoneString) {
		String space = getSpace(nameString);
		list.getItems().add("\t\t" + nameString + space + "\t\t\t\t\t" + phoneString);
		list.getItems().sort(null);
	}
	
	public static String getSpace(String s) {
		int len = s.length();
		int size = 70-(len);
		char[] tab = new char[size];
		for(int i = 0 ; i<size ; i++) {
			tab[i] = ' ';
		}
		return String.valueOf(tab);
	}
}

