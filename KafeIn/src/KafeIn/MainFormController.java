/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KafeIn;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Desktop;



/**
 *
 * @author 0xwighozali
 */
public class MainFormController implements Initializable {

    @FXML
    private Button customer_btn;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button inventory_addBtn;

    @FXML
    private Button inventory_clearBtn;

    @FXML
    private TableColumn<ProductData, String> inventory_col_date;

    @FXML
    private TableColumn<ProductData, String> inventory_col_idProduct;

    @FXML
    private TableColumn<ProductData, String> inventory_col_nmProduct;

    @FXML
    private TableColumn<ProductData, String> inventory_col_price;

    @FXML
    private TableColumn<ProductData, String> inventory_col_stock;

    @FXML
    private TableColumn<ProductData, String> inventory_col_type;

    @FXML
    private Button inventory_deleteBtn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private Button inventory_importBtn;

    @FXML
    private TextField inventory_price;

    @FXML
    private TextField inventory_productID;

    @FXML
    private TextField inventory_productName;

    @FXML
    private TextField inventory_stock;

    @FXML
    private TableView<ProductData> inventory_tableView;

    @FXML
    private ComboBox<?> inventory_type;

    @FXML
    private Button inventory_updateBtn;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button menu_btn;

    @FXML
    private TableColumn<ProductData, String> menu_col_price;

    @FXML
    TableColumn<ProductData, String> menu_col_productName;

    @FXML
    private TableColumn<ProductData, String> menu_col_quantity;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_payBtn;

    @FXML
    private TextField menu_amount;

    @FXML
    private TextField menu_total;

    @FXML
    private Label menu_change;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private Button menu_clearBtn;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private TableView<ProductData> menu_tableView;

    @FXML
    private TextField menu_orderBy;

    @FXML
    private Label menu_totalPrice;

    @FXML
    private Button transaction_btn;

    @FXML
    private TableColumn<ProductData, String> transaction_col_customer;

    @FXML
    private TableColumn<ProductData, String> transaction_col_date;

    @FXML
    private TableColumn<ProductData, String> transaction_col_total;

    @FXML
    private TableView<ProductData> transaction_tableView;

    @FXML
    private AnchorPane transaction_form;

    @FXML
    private TableColumn<ProductData, String> col_transaction;

    private Alert alert;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;

    private void showAlert(AlertType type, String title, String content) {
        alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
    }

    public void switchFrom(ActionEvent event) {
        if (event.getSource() == inventory_btn) {
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            transaction_form.setVisible(false);
            inventoryTypeList();
            inventoryShowData();
        } else if (event.getSource() == menu_btn) {
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            transaction_form.setVisible(false);
            menuDisplayCard();
        } else if (event.getSource() == transaction_btn) {
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            transaction_form.setVisible(true);
            transactionShowData();
        }
    }

    public ObservableList<ProductData> inventoryDataList() {
        List<ProductData> listData = new ArrayList<>();

        String query = "SELECT * FROM product";

        try (Connection connect = Database.connectDB();
                PreparedStatement prepare = connect.prepareStatement(query);
                ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                ProductData prodData = new ProductData(
                        result.getString("product_id"),
                        result.getString("product_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date")
                );
                listData.add(prodData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(listData);
    }

    public void inventoryShowData() {
        inventory_col_idProduct.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_col_nmProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_tableView.setItems(inventoryDataList());
        inventory_tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private String[] typeList = {"Makanan", "Minuman"};

    public void inventoryTypeList() {
        List<String> typeL = new ArrayList<>();

        for (String data : typeList) {
            typeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);
        inventory_type.setItems(listData);
    }

    public void inventoryAddBtn() {
        if (inventory_productID.getText().isEmpty()
                || inventory_productName.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || Data.path == null) {
            showAlert(AlertType.ERROR, "Error Message", "Please fill all blank field");
            alert.showAndWait();
        } else {
            String checkProdID = "SELECT product_id FROM product WHERE product_id = '"
                    + inventory_productID.getText() + "'";
            connect = Database.connectDB();

            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);

                if (result.next()) {
                    showAlert(AlertType.ERROR, "Error Message", inventory_productID.getText() + " is already taken");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO product "
                            + "(product_id, product_name, type, stock, price, image, date) "
                            + "VALUES(?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, inventory_productID.getText());
                    prepare.setString(2, inventory_productName.getText());
                    prepare.setString(3, (String) inventory_type.getSelectionModel().getSelectedItem());
                    prepare.setString(4, inventory_stock.getText());
                    prepare.setString(5, inventory_price.getText());

                    String path = Data.path;
                    path = path.replace("\\", "\\\\");
                    prepare.setString(6, path);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(7, String.valueOf(sqlDate));
                    prepare.executeUpdate();
                    showAlert(AlertType.INFORMATION, "Error Message", "Successfully added");
                    alert.showAndWait();
                    inventoryShowData();
                    inventoryClearBtn();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inventoryClearBtn() {
        inventory_productID.setText("");
        inventory_productName.setText("");
        inventory_type.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        Data.path = "";
        inventory_imageView.setImage(null);
    }

    public void inventoryUpdateBtn() {
        String path = Data.path;
        path = path.replace("\\", "\\\\");

        if (inventory_productID.getText().isEmpty()
                || inventory_productName.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || Data.path == null) {
            showAlert(AlertType.ERROR, "Error Message", "Please fill all blank fields");
            alert.showAndWait();
        } else {
            String updateData = "UPDATE product SET "
                    + "product_name=?, "
                    + "type=?, "
                    + "stock=?, "
                    + "price=?, "
                    + "image=?, "
                    + "date=? "
                    + "WHERE product_id=?";

            try {
                connect = Database.connectDB();
                prepare = connect.prepareStatement(updateData);

                prepare.setString(1, inventory_productName.getText());
                prepare.setString(2, (String) inventory_type.getSelectionModel().getSelectedItem());
                prepare.setString(3, inventory_stock.getText());
                prepare.setString(4, inventory_price.getText());
                prepare.setString(5, Data.path);
                prepare.setString(6, Data.date);
                prepare.setString(7, inventory_productID.getText());

                showAlert(AlertType.CONFIRMATION, "Confirmation", "Are you sure you want to UPDATE product ID: " + inventory_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                    prepare.executeUpdate();

                    showAlert(AlertType.INFORMATION, "Success", "Successfully Updated!");
                    alert.showAndWait();
                    inventoryShowData();
                    inventoryClearBtn();
                } else {
                    showAlert(AlertType.ERROR, "Information", "Update operation canceled");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Error Message", "Failed to update product");
                alert.showAndWait();
            } finally {
                try {
                    if (prepare != null) {
                        prepare.close();
                    }
                    if (connect != null) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void InventoryImportBtn() {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg", "*jpeg", "*jfif"));

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            Data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 120, 127, false, true);

            inventory_imageView.setImage(image);
        }
    }

    public void inventorySelectData() {
        ProductData prodData = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        inventory_productID.setText(prodData.getProductId());
        inventory_productName.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));
        Data.path = prodData.getImage();
        String path = "File:" + prodData.getImage();
        Data.date = String.valueOf(prodData.getDate());
        image = new Image(path, 120, 127, false, true);
        inventory_imageView.setImage(image);

    }

    public void inventoryDeleteBtn() {
        if (inventory_productID.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error Message", "Please enter Product ID to delete");
            alert.showAndWait();
        } else {
            showAlert(AlertType.CONFIRMATION, "Confirmation", "Are you sure you want to DELETE product ID: " + inventory_productID.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM product WHERE product_id=?";

                try {
                    connect = Database.connectDB();
                    prepare = connect.prepareStatement(deleteData);

                    prepare.setString(1, inventory_productID.getText());

                    int rowsAffected = prepare.executeUpdate();

                    if (rowsAffected > 0) {
                        showAlert(AlertType.INFORMATION, "Success", "Successfully Deleted!");
                        alert.showAndWait();
                        inventoryShowData();
                        inventoryClearBtn();
                    } else {
                        showAlert(AlertType.ERROR, "Error Message", "Product ID not found");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert(AlertType.ERROR, "Error Message", "Failed to delete product");
                    alert.showAndWait();
                } finally {
                    try {
                        if (prepare != null) {
                            prepare.close();
                        }
                        if (connect != null) {
                            connect.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                showAlert(AlertType.ERROR, "Error Message", "Cancelled");
                alert.showAndWait();
            }
        }
    }

    public void menuClearBtn() {
        menu_tableView.getItems().clear();
        menu_totalPrice.setText("Rp.0");
        menu_amount.setText("");
        menu_change.setText("Rp.0");
        menu_orderBy.setText("");

    }

    public void menuRemoveBtn() {
        ProductData selectedProduct = menu_tableView.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            menu_tableView.getItems().remove(selectedProduct);
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "No item selected for remove.");
            alert.showAndWait();
        }
    }

    private ObservableList<ProductData> menuDataList = FXCollections.observableArrayList();

    public void menuShowDataOrder(ProductData productData) {
        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        menuDataList.add(productData);

        menu_tableView.setItems(menuDataList);
        menu_tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        menuGetTotal();
    }

    public ObservableList<ProductData> menuGetData() {
        String query = "SELECT * FROM product";

        List<ProductData> listData = new ArrayList<>();

        try (Connection connect = Database.connectDB();
                PreparedStatement prepare = connect.prepareStatement(query);
                ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                ProductData prod = new ProductData(
                        result.getString("product_id"),
                        result.getString("product_name"),
                        result.getDouble("price"),
                        result.getString("image")
                );

                listData.add(prod);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(listData);
    }

    private double change;
    private double amount;

    public void menuAmount() {
        menuGetTotal();
        if (menu_amount.getText().isEmpty() || totalFromTableView == 0) {
            showAlert(AlertType.ERROR, "Error Message", "Invalid");
        } else {
            amount = Double.parseDouble(menu_amount.getText());

            if (amount < totalFromTableView) {
                menu_amount.setText("");
            } else {
                change = (amount - totalFromTableView);
                menu_change.setText("Rp." + change);
            }
        }
    }

    public void menuPayBtn() throws SQLException {
        if (totalFromTableView == 0) {
            showAlert(AlertType.ERROR, "Error Message", "Please choose order first!");

        } else {
            updateProductStock();
            menuGetTotal();

            connect = Database.connectDB();

            try {
                showAlert(AlertType.CONFIRMATION, "Confirmation Message", "Are you sure? to pay?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String insertPay = "INSERT INTO receipt (customer_name, total, date) VALUES(?, ?, ?)";
                    prepare = connect.prepareStatement(insertPay);
                    prepare.setString(1, menu_orderBy.getText());
                    prepare.setString(2, String.valueOf(totalFromTableView));
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(3, String.valueOf(sqlDate));
                    prepare.executeUpdate();
                    showAlert(AlertType.INFORMATION, "Error Message", "Successfully added");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateProductStock() {
        ObservableList<ProductData> orderedProducts = menu_tableView.getItems();

        for (ProductData orderedProduct : orderedProducts) {
            String prodID = orderedProduct.getProductId();
            int orderedQuantity = orderedProduct.getQuantity();
            connect = Database.connectDB();
            try {
                String getCurrentStockQuery = "SELECT stock FROM product WHERE product_id = ?";
                prepare = connect.prepareStatement(getCurrentStockQuery);
                prepare.setString(1, prodID);
                result = prepare.executeQuery();

                if (result.next()) {
                    int currentStock = result.getInt("stock");
                    int updatedStock = currentStock - orderedQuantity;

                    String updateStockQuery = "UPDATE product SET stock = ? WHERE product_id = ?";
                    prepare = connect.prepareStatement(updateStockQuery);
                    prepare.setInt(1, updatedStock);
                    prepare.setString(2, prodID);
                    prepare.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void menuDisplayCard() {
        ObservableList<ProductData> cardListData = menuGetData();

        int row = 0;
        int column = 0;

        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        for (int i = 0; i < cardListData.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CardProduct.fxml"));
                AnchorPane pane = loader.load();
                CardProductController cardC = loader.getController();
                cardC.setData(cardListData.get(i));
                cardC.setMainFormController(this);
                cardC.setData(cardListData.get(i));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }
                menu_gridPane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logout() {
        try {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Apakah Anda Yakin Mau Keluar?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                logout_btn.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("KafeIn");
                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Double totalFromTableView;

    public void menuGetTotal() {
        totalFromTableView = 0.0;

        for (ProductData productData : menuDataList) {
            totalFromTableView += productData.getPrice();
        }

        menu_totalPrice.setText("Rp." + totalFromTableView);
    }

    public ObservableList<ProductData> transactionDataList() {
        List<ProductData> listData = new ArrayList<>();

        String query = "SELECT * FROM receipt";

        try (Connection connect = Database.connectDB();
                PreparedStatement prepare = connect.prepareStatement(query);
                ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                ProductData prodData = new ProductData(
                        result.getInt("id"),
                        result.getString("customer_name"),
                        result.getInt("total"),
                        result.getDate("date")
                );
                listData.add(prodData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(listData);
    }

    public void transactionShowData() {
        col_transaction.setCellValueFactory(new PropertyValueFactory<>("txId"));
        transaction_col_customer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        transaction_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        transaction_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        transaction_tableView.setItems(transactionDataList());
        transaction_tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    public void cetakStruck() {
        Document document = new Document();

        try {
            String fileName = "Struk_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Tambahkan informasi atau header pada PDF
            document.add(new Paragraph("Struk Pembelian"));
            document.add(new Paragraph(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
            document.add(new Paragraph("==================================="));

            // Ambil data dari menu_tableView dan tambahkan ke PDF
            ObservableList<ProductData> orderedProducts = menu_tableView.getItems();
            for (ProductData orderedProduct : orderedProducts) {
                document.add(new Paragraph(
                        "Nama Produk: " + orderedProduct.getProductName() +
                        "\nJumlah: " + orderedProduct.getQuantity() +
                        "\nHarga: Rp." + orderedProduct.getPrice() +
                        "\n------------------------------"
                ));
            }

            // Tambahkan total harga
            document.add(new Paragraph("Total Harga: Rp." + totalFromTableView));

            document.close();

            showAlert(AlertType.INFORMATION, "Informasi", "Struk berhasil dicetak. File: " + fileName);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error Message", "Gagal mencetak struk.");
            alert.showAndWait();
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventoryTypeList();
        inventoryShowData();

        transactionShowData();
        menuGetTotal();
        menuDisplayCard();

    }

}
