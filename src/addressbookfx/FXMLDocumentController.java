/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbookfx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Kimmo
 */
public class FXMLDocumentController implements Initializable {
    
    private final String STORAGEFILE = "addressbook.txt";
    private File storageFile = null;
    private ArrayList<UserInfo> bookContent;
    
    @FXML
    TitledPane titledPaneAddNew;

    @FXML
    public TextField textFieldAddFirstName;

    @FXML
    TextField textFieldAddLastName;
    
    @FXML
    TextField textFieldAddAddress;
    
    @FXML
    TextField textFieldAddPhone;
    
    @FXML
    TextField textFieldAddEmail;
    
    @FXML
    TitledPane titledPaneSearch;
    
    @FXML
    TextField textFieldSearchText;
    
    @FXML
    TextArea textAreaForData;
    
    @FXML
    private void handleTilePaneAddNewClick(MouseEvent event) {
        
        // NOTE: This does not work correctly if used hastily
        // both lists can end up open/closed at the same time
        // for some (timing?) reason.
        //
        // Also bad idea to update the TextArea every time
        // since the method put the whole bookContent array
        // there... Works with tiny content, but if it gets
        // bigger things will get worse.
        
        if (titledPaneSearch.isExpanded()) {
            titledPaneSearch.setExpanded(false);
            titledPaneAddNew.setExpanded(true);
            updateInfoAreaWithEverything();
        } else {
            titledPaneSearch.setExpanded(true);
            titledPaneAddNew.setExpanded(false);
        }
    }

    @FXML
    private void handleSaveAction(ActionEvent event) {
            
        String first = textFieldAddFirstName.getText();
        String last = textFieldAddLastName.getText();
        String address = textFieldAddAddress.getText();
        String phone = textFieldAddPhone.getText();
        String email = textFieldAddEmail.getText();

        // At least the name must be given
        
        if (first.isEmpty() == false && last.isEmpty() == false) {
            
            // Put something in empty fields
            address = (address.isEmpty())?("no address"):(address);
            phone = (phone.isEmpty())?("no phone number"):(phone);
            email = (email.isEmpty())?("no e-mail"):(email);
            
            bookContent.add(new UserInfo(first,last,address,phone,email));
           
            // Sort the list with the added info, UserInfo has compareTo
            Collections.sort(bookContent);
            
            // Update the addressbook file
            writeAddressBook(storageFile);
            
            textFieldAddFirstName.clear();
            textFieldAddLastName.clear();
            textFieldAddAddress.clear();
            textFieldAddPhone.clear();
            textFieldAddEmail.clear();
            
            updateInfoAreaWithEverything();

        }
    }
    
    @FXML
    private void handleTilePaneSearchClick(MouseEvent event) {
        
        // NOTE: This does not work correctly if used hastily
        // both lists can end up open/closed at the same time
        // for some (timing?) reason.
        //
        // Also bad idea to update the TextArea every time
        // since the method put the whole bookContent array
        // there... Works with tiny content, but if it gets
        // bigger things will get worse.
        
        if (titledPaneAddNew.isExpanded()) {
            titledPaneAddNew.setExpanded(false);
            titledPaneSearch.setExpanded(true);
        } else {
            titledPaneAddNew.setExpanded(true);
            titledPaneSearch.setExpanded(false);
            updateInfoAreaWithEverything();
        }
    }
    
    @FXML
    private void handleSearchAction(ActionEvent event) {
        String find = textFieldSearchText.getText();
        if (find != null && find.isEmpty() == false) {
            ArrayList<UserInfo> infos = new ArrayList();
            for (UserInfo info : bookContent) {
                if (info.findText(find)) {
                    infos.add(info);
                }
            }
            if (infos.isEmpty()) {
                noMatchFound();
            } else {
                updateInfoAreaWithSearchResult(infos);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        storageFile = new File(STORAGEFILE);
        
        if (storageFile.exists()) {
            readAddressBook(storageFile);
            updateInfoAreaWithEverything();
        } else {
            bookContent = new ArrayList();
        }
    }    
    
    private void updateInfoAreaWithEverything() {
        textAreaForData.clear();
        for (UserInfo info : bookContent) {
            textAreaForData.appendText(info.getInfo()+"\n");
        }
    }
    
    private void updateInfoAreaWithSearchResult(ArrayList<UserInfo> infos) {
        textAreaForData.clear();
        for (UserInfo info : infos) {
            textAreaForData.appendText(info.getInfo()+"\n");
        }

    }
    
    private void noMatchFound() {
        textAreaForData.clear();
        textAreaForData.appendText("Sorry, nothing found.");
    }
    
    private boolean readAddressBook(File file) {
        boolean result = false;
        FileInputStream fs = null;
        ObjectInputStream os = null;
        try {
            fs = new FileInputStream(file);
            os = new ObjectInputStream(fs);
            bookContent = (ArrayList) os.readObject();
            os.close();
            result = true;
        } catch (Exception e) {
            System.out.println("ERROR: File/ObjectInputStream operation fails. "+e);
            System.exit(1);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    System.out.println("ERROR: ObjectInputStream closing fails. "+e);
                    System.exit(1);
                }
            }
        }
        
        return result;
    }
    
    private void writeAddressBook(File file) {
        FileOutputStream fs = null;
        ObjectOutputStream os = null;
        try {
            fs = new FileOutputStream(file);
            os = new ObjectOutputStream(fs);
            os.writeObject(bookContent);
            os.close();
        } catch (Exception e) {
            System.out.println("ERROR: File/ObjectOutputStream operation fails. "+e);
            System.exit(1);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    System.out.println("ERROR: ObjectOutputStream closing fails. "+e);
                    System.exit(1);
                }
            }
        }
    }
}
    
