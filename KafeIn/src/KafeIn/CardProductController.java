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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author 0xwighozali
 */
public class CardProductController implements Initializable {

    private MainFormController mainFormController;

    // Constructor to receive reference to MainFormController
    public void setMainFormController(MainFormController mainFormController) {
        this.mainFormController = mainFormController;
    }

    @FXML
    private Button card_addBtn;

    @FXML
    private AnchorPane card_form;

    @FXML
    private ImageView card_imageView;

    @FXML
    private Label card_productName;

    @FXML
    private Label card_productPrice;

    @FXML
    private Spinner<Integer> card_spinner;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Image image;
    private Alert alert;

    private SpinnerValueFactory<Integer> spin;
    private ProductData prodData;
    private String prodID;

    private void showAlert(Alert.AlertType type, String title, String content) {
        alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
    }

    public void setData(ProductData prodData) {
        this.prodData = prodData;
        prodID = prodData.getProductId();
        card_productName.setText(prodData.getProductName());
        card_productPrice.setText("Rp." + String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        image = new Image(path, 190, 94, false, true);
        card_imageView.setImage(image);
        pr = prodData.getPrice();
    }

    private int qty;

    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        card_spinner.setValueFactory(spin);
    }

    private double totalP;
    private double pr;

    public void cardAddBtn() {

        qty = card_spinner.getValue();
        connect = Database.connectDB();

        try {
            int checkStock = 0;

            String checkStck = "SELECT stock FROM product where product_id = '"
                    + prodID + "'";
            prepare = connect.prepareStatement(checkStck);
            result = prepare.executeQuery();

            if (result.next()) {
                checkStock = result.getInt("stock");
            }

            if (checkStock < qty) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Out of stock");
                alert.showAndWait();
            } else {
                totalP = (qty * pr);
                ProductData productData = new ProductData(prodID, card_productName.getText(), qty, totalP);
                mainFormController.menuShowDataOrder(productData);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setQuantity();

    }

}
