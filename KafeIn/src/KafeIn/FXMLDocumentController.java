/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KafeIn;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author 0xwighozali
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button fl_loginBtn;

    @FXML
    private PasswordField fl_password;

    @FXML
    private TextField fl_username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;
    
    private void showAlert(AlertType type, String title, String content) {
        alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void loginBtn() {
        if (fl_username.getText().isEmpty() || fl_password.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error Message", "Nama Pengguna atau password salah");
        } else {
            String query = "SELECT username, password FROM user WHERE username = ? and password = ?";
            connect = Database.connectDB();

            try {
                prepare = connect.prepareStatement(query);
                prepare.setString(1, fl_username.getText());
                prepare.setString(2, fl_password.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    showAlert(AlertType.INFORMATION, "Information Message", "Berhasil Login");

                    Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setTitle("KafeIn");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);
                    stage.setScene(scene);
                    stage.show();

                    fl_loginBtn.getScene().getWindow().hide();
                } else {
                    showAlert(AlertType.ERROR, "Error Message", "Nama Pengguna atau password salah");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
