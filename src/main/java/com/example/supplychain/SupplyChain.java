package com.example.supplychain;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;

public class SupplyChain extends Application {
    ArrayList<Product> kartproductdata=new ArrayList<>();
    ObservableList<Product>obl;
    Button loginButton;

    Pane bodyPane;
Pane root;
    boolean customerLoggedIn = false;
    String customerEmail = "";
    Pane bottomPane = new Pane();
    Pane topPane = new Pane();
    Label welcomeUser;

    public static final int width = 700, height = 400, upperLine = 50;

    ProductDetails productDetails = new ProductDetails();
    Orderdetails orderdetails = new Orderdetails();

    private Pane headerBar(){

        topPane.setPrefSize(width, upperLine-10);

        TextField searchText = new TextField();
        searchText.setPromptText("Please search here");
        searchText.setTranslateX(80);
        int searchEnd = 400;
        Button searchButton = new Button("Search");
        Button signup = new Button("Sign Up");
signup.setTranslateX(390);
Button adminlogin=new Button("Admin View");
        adminlogin.setTranslateX(470);
        searchButton.setTranslateX(250);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String search = searchText.getText();
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productDetails.getProductsByName(search));
            }
        });
        adminlogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String search = searchText.getText();
                topPane.setVisible(false);
                bottomPane.setVisible(false);
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(adminpage());
            }
        });

        loginButton = new Button("Login");
        loginButton.setTranslateX(310);


        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bottomPane.setVisible(false);
                topPane.setVisible(false);
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
            }
        });
        signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                topPane.setVisible(false);
                bottomPane.setVisible(false);
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(register());
            }
        });

        welcomeUser = new Label("Hey! There");
        welcomeUser.setTranslateX(searchEnd + 160);
        Kart kart=new Kart(20, Color.RED);
Rectangle rectkart=kart.getkart();
        rectkart.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                bottomPane.setVisible(false);
                topPane.setVisible(false);

                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(kartpane());

            }
        });
        topPane.getChildren().addAll(searchText, searchButton, loginButton,  welcomeUser,signup,rectkart,adminlogin);
        topPane.setTranslateY(10);

        return topPane;
    }

    private Pane footerBar(){

        bottomPane.setPrefSize(width, upperLine-10);
        bottomPane.setTranslateY(upperLine+height+10);

        int searchEnd = 400;
        Button buyNowButton = new Button("Buy Now");
        Button addtokart = new Button("Add To Kart");
        buyNowButton.setTranslateX(searchEnd);
        addtokart.setTranslateX(searchEnd+80);

        Dialog<String> dialog1 = new Dialog<>();

        dialog1.setContentText("Order Placed");

        Dialog<String> dialog2 = new Dialog<>();

        dialog2.setContentText("Please select product");
        Dialog<String> dialog3 = new Dialog<>();

        dialog3.setContentText("Please Login  and select product");

        Dialog<String> dialog4 = new Dialog<>();
        dialog4.setContentText("Added to kart");
        ButtonType buttonType = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog2.getDialogPane().getButtonTypes().add(buttonType);
        dialog3.getDialogPane().getButtonTypes().add(buttonType);
        dialog1.getDialogPane().getButtonTypes().add(buttonType);
        dialog4.getDialogPane().getButtonTypes().add(buttonType);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                String userEmail;
                int productId;
                userEmail = customerEmail;
                productId = productDetails.getProductId();

                if(!userEmail.isBlank() && productId!=-1){
                    boolean status =  Order.orderProduct(productId,userEmail);
                    if(status){
                        dialog1.showAndWait();
                    }
                    else {
                        dialog2.showAndWait();
                    }
                }
                else {
                    dialog3.showAndWait();
                }

            }
        });
        addtokart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String userEmail;
                int productId;
                userEmail = customerEmail;
                productId = productDetails.getProductId();

                kartproductdata.add(productDetails.getProduct());
                if(!userEmail.isBlank() && productId!=-1)
                {
                        dialog4.showAndWait();
                    }
                else {
                    dialog3.showAndWait();
                }

            }
        });

        bottomPane.getChildren().addAll(buyNowButton,addtokart);
        return bottomPane;
    }

    private GridPane loginPage(){
        Label emilLabel = new Label("E-mail");
        Label passwordLabel = new Label("Password");
        TextField emailText = new TextField();
        emailText.setPromptText("Please enter email");
        PasswordField passwordText = new PasswordField();
        passwordText.setPromptText("Please enter password");

        Button localLoginButton = new Button("Login");

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Login Message");
        dialog.setContentText("Login failed !! Please enter correct email and password!");

        ButtonType buttonType = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().add(buttonType);


        localLoginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = emailText.getText();
                String password = passwordText.getText();
                if(email.isBlank() || password.isBlank()){
                    // please igve mailand password
                }
                else{
                    if(Login.customerLogin(email,password)){
                        customerLoggedIn = true;
                        customerEmail = email;
                        welcomeUser.setText("Welcome " + email);
                        bodyPane.getChildren().clear();
                        topPane.setVisible(true);
                        bottomPane.setVisible(true);
                        bodyPane.getChildren().add(productDetails.getAllProducts());
                    }
                    else{
                        welcomeUser.setText("Login Failed");
                        emailText.clear();
                        passwordText.clear();
                        dialog.showAndWait();

                    }
                }

            }
        });

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                emailText.setText("");
                passwordText.setText("");
            }
        });
Button back1=new Button("back");
        back1.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                topPane.setVisible(true);
                bottomPane.setVisible(true);
                bodyPane.getChildren().add(productDetails.getProductsByName(""));
            }
        });
        GridPane gridPane = new GridPane();

        gridPane.setMinSize(bodyPane.getWidth(), bodyPane.getHeight());

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(emilLabel, 0,0);
        gridPane.add(emailText,1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordText, 1, 1);
        gridPane.add(localLoginButton, 0, 2);
        gridPane.add(clearButton, 1, 2);
        gridPane.add(back1, 2, 2);



        return gridPane;
    }
    private GridPane register(){

        Label emilLabel = new Label("E-mail *");
        Label passwordLabel = new Label("Password *");
        Label Fname = new Label("First Name");
        Label Lname = new Label("Last Name");
        Label addrlabel = new Label("address");
        Label mobile = new Label("Mobile *");
        TextField fnametextfield = new TextField();
        TextField lnametextfield = new TextField();
        TextField addrtextfield = new TextField();
        TextField mobiletextfield = new TextField();
        TextField emailText = new TextField();
        emailText.setPromptText("Please enter email");
        PasswordField passwordText = new PasswordField();
        passwordText.setPromptText("Please enter password");

        Button register = new Button("Register");
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Register Message");
        dialog.setContentText("Registration failed !! Please enter correct details!");
        Dialog<String> exists = new Dialog<>();
        exists.setTitle("Register Message");
        exists.setContentText("Registration failed !!user already existed!");
        Dialog<String> notemptydialog = new Dialog<>();
        notemptydialog.setTitle("Register Message");
        notemptydialog.setContentText("* Fields Should Not Be Empty");
        ButtonType buttonType = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        exists.getDialogPane().getButtonTypes().add(buttonType);
        notemptydialog.getDialogPane().getButtonTypes().add(buttonType);
        Button back=new Button("back");


        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                topPane.setVisible(true);
                bottomPane.setVisible(true);
                bodyPane.getChildren().add(productDetails.getProductsByName(""));
            }
        });
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                String email = emailText.getText();
                String password = passwordText.getText();
                String fnamedata = emailText.getText();
                String lnamedata = passwordText.getText();
                String addrdata = emailText.getText();
                long mobiledata;
                String mobi=mobiletextfield.getText();
                if(!mobi.isEmpty()) {
                    mobiledata = Long.parseLong(mobi);
                }
                else
                {
                mobiledata=0;
                }

                if(Login.isexists(email)) {
                exists.showAndWait();
                }
                else {

                    if(email.isBlank() || password.isBlank()||mobiledata<=0) {
                    notemptydialog.showAndWait();
                }
                else {
                    if (Login.customerreg(email, password, fnamedata, lnamedata, addrdata, mobiledata)) {
                        customerLoggedIn = true;
                        customerEmail = email;
                        welcomeUser.setText("Welcome " + email);
                        bodyPane.getChildren().clear();
                        topPane.setVisible(true);
                        bottomPane.setVisible(true);
                        bodyPane.getChildren().add(productDetails.getAllProducts());
                    } else {
                        welcomeUser.setText("registration Failed");
                        emailText.clear();
                        passwordText.clear();
                        dialog.showAndWait();

                    }
                }
                }

            }
        });

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                emailText.setText("");
                passwordText.setText("");
            }
        });

        GridPane gridPane = new GridPane();

        gridPane.setMinSize(bodyPane.getWidth(), bodyPane.getHeight());

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);
        gridPane.setHgap(4);

        gridPane.add(emilLabel, 0,0);
        gridPane.add(passwordLabel,0, 1);
        gridPane.add(Fname, 0,2);
        gridPane.add(Lname,0, 3);
        gridPane.add(addrlabel, 0, 4);
        gridPane.add(mobile, 0, 5);
        gridPane.add(register, 0, 6);
        gridPane.add(clearButton, 1, 6);
        gridPane.add(back, 2, 6);
        gridPane.add(emailText, 1, 0);
        gridPane.add(passwordText, 1, 1);
        gridPane.add(fnametextfield, 1, 2);
        gridPane.add(lnametextfield, 1, 3);
        gridPane.add(addrtextfield, 1, 4);
        gridPane.add(mobiletextfield, 1, 5);



        return gridPane;
    }
public Pane kartpane()
{
    Pane kpane=new Pane();
    Button Back=new Button("back");
    Back.setTranslateX(320);
    Back.setTranslateY(310);
    Button orderall=new Button("orderall");

    orderall.setTranslateX(370);
    orderall.setTranslateY(310);
    Back.setOnAction(new EventHandler<ActionEvent>() {
        @Override

        public void handle(ActionEvent actionEvent) {
            bodyPane.getChildren().clear();
            bottomPane.setVisible(true);
            topPane.setVisible(true);
            bodyPane.getChildren().add(productDetails.getProductsByName(""));
        }
    });
    Button del=new Button("remove from kart");
    del.setTranslateX(460);
    del.setTranslateY(310);
    TableView<Product> karttable=new TableView<>();
    karttable.setPrefSize(650,200);
ObservableList<Product>ob=FXCollections.<Product>observableArrayList();;
ob.addAll(kartproductdata);
    TableColumn idCol = new TableColumn("Id");
    idCol.setCellValueFactory( new PropertyValueFactory<>("id"));
    TableColumn productname = new TableColumn("Product");
    productname.setCellValueFactory( new PropertyValueFactory<>("name"));
    TableColumn price = new TableColumn("Price");
    price.setCellValueFactory( new PropertyValueFactory<>("price"));

    karttable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    karttable.setItems(ob);
    ProductDetails p=new ProductDetails();
    karttable.getColumns().addAll(idCol,productname,price);
    kpane.getChildren().addAll(Back,karttable,orderall,del);
    karttable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    del.setOnAction(new EventHandler<ActionEvent>()
        { @Override
        public void handle(ActionEvent actionEvent) {

            Product selectedItem = karttable.getSelectionModel().getSelectedItem();
            kartproductdata.remove(selectedItem);
    karttable.getItems().remove(selectedItem);}});


    Dialog<String> dialog1 = new Dialog<>();

    dialog1.setContentText("Order Placed");

    Dialog<String> dialog2 = new Dialog<>();

    dialog2.setContentText("Please add products to cart");
    Dialog<String> dialog3 = new Dialog<>();

    dialog3.setContentText("Please Login");

    ButtonType buttonType = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
    dialog1.getDialogPane().getButtonTypes().add(buttonType);
    dialog2.getDialogPane().getButtonTypes().add(buttonType);
    dialog3.getDialogPane().getButtonTypes().add(buttonType);
    String userEmail;
    userEmail = customerEmail;
    karttable.getSelectionModel().select(0);
    orderall.setOnAction(new EventHandler<ActionEvent>() {
        @Override

        public void handle(ActionEvent actionEvent) {

            int kartsize=kartproductdata.size();
            for(int i=1;i<=kartsize;i++) {
                System.out.println(i);
                karttable.getSelectionModel().focus(1);

                ObservableList<Product> selectedRows = karttable.getSelectionModel().getSelectedItems();
// we don't want to iterate on same collection on with we remove items
                ArrayList<Product> rows = new ArrayList<>(selectedRows);
Product currproduct=karttable.getSelectionModel().getSelectedItem();
                ArrayList<Integer> rows1 = new ArrayList<>();
            rows.forEach(currproduct1->rows1.add(currproduct1.getId()));
                System.out.println(rows1);
int productId=rows1.get(0);

                if(!userEmail.isBlank() && productId!=-1){
                    boolean status =  Order.orderProduct(productId,userEmail);

                }
                else {
                    dialog3.showAndWait();
                }
                karttable.getItems().remove(currproduct);
            kartproductdata.remove(currproduct);
            }}
    });


return kpane;
}
    public Pane adminpage()
    {
        Pane adminpane=new Pane();
        Button Back=new Button("back");
        Back.setTranslateX(320);
        Back.setTranslateY(440);

        Button getorders=new Button("Show Orders");
        getorders.setTranslateX(370);
        getorders.setTranslateY(440);

        getorders.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent actionEvent) {

                /*TableView<Integer> t1=new TableView<>();
                TableColumn num=new TableColumn<>("nums");
                ObservableList<Integer>ob= FXCollections.observableArrayList();
                ob.add(1);
                ob.add(2);
                t1.setItems(ob);
                t1.getColumns().addAll(num);*/
                Pane orderpane=productDetails.getAllProductsadmin();
                orderpane.setPrefSize(100,100);
                bodyPane.getChildren().add(orderpane);
            }
        });

        Back.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bottomPane.setVisible(true);
                topPane.setVisible(true);
                bodyPane.getChildren().add(productDetails.getAllProducts());
            }
        });

adminpane.getChildren().addAll(Back,getorders);


        return adminpane;
    }
    private Pane createContent(){
        root = new Pane();
        root.setPrefSize(width, height+2*upperLine+20);


        bodyPane = new Pane();
        bodyPane.setPrefSize(width,height);
        bodyPane.setTranslateY(upperLine);

        bodyPane.getChildren().add(productDetails.getAllProducts());

        root.getChildren().addAll(headerBar(), bodyPane, footerBar());


        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.getIcons().add(new Image("C:\\Users\\dell\\Downloads\\supplychain-master\\src\\main\\java\\com\\example\\supplychain\\images.png"));

        stage.setTitle("Supply Chain!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}