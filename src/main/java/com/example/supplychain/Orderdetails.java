package com.example.supplychain;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Orderdetails {

     TableView<Orderobj> productTable;

    public  Pane getAllProducts(){
        TableView<Orderobj> table1 = new TableView<>();
        //Columns
        TableColumn idCol1 = new TableColumn("tity");


        TableColumn nameCol1 = new TableColumn("cid");
        TableColumn priceCol1 = new TableColumn("pid");

        ObservableList<Orderobj> data = FXCollections.observableArrayList(
                new Orderobj(2, 2, 2),
                new Orderobj(2, 2, 37)
        );

        ObservableList<Orderobj> productData = Orderobj.getAllProducts();

        table1.setItems(productData);
        table1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table1.getColumns().addAll(idCol1, nameCol1, priceCol1);
        table1.setPrefSize(SupplyChain.width-10, SupplyChain.height-10);

        productTable= table1;
        Pane tpane = new Pane();
        tpane.getChildren().add(table1);
        tpane.setPrefSize(SupplyChain.width, SupplyChain.height);
//        tpane.setTranslateY(100);
        return tpane;
    }

    public Pane getProductsByName(String searchText){
        TableView<Orderobj> table = new TableView<>();

        //Columns
        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory( new PropertyValueFactory<>("quantity"));
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory( new PropertyValueFactory<>("cid"));
        TableColumn priceCol = new TableColumn("Price");
        priceCol.setCellValueFactory( new PropertyValueFactory<>("pid"));

        ObservableList<Orderobj> data = FXCollections.observableArrayList(
                new Orderobj(2, 5, 3456),
                new Orderobj(2, 5, 3747)
        );

        ObservableList<Orderobj> productData = Orderobj.getProductsByName(searchText);

        table.setItems(productData);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.getColumns().addAll(idCol, nameCol, priceCol);
        table.setPrefSize(SupplyChain.width-10, SupplyChain.height-10);

        productTable = table;
        Pane tpane = new Pane();
        tpane.setPrefSize(SupplyChain.width, SupplyChain.height);
        tpane.getChildren().add(table);
//        tpane.setTranslateY(100);
        return tpane;
    }

    public void getSelectedRowValues(){

        TableView<Orderobj> table = productTable;
        if(productTable==null){
            System.out.printf("Table object not found");
            return;
        }
        if (productTable.getSelectionModel().getSelectedIndex() != -1) {
            Orderobj selectedProduct = productTable.getSelectionModel().getSelectedItem();
            System.out.println("Angad");
            System.out.println(selectedProduct.getquantity() + " " + selectedProduct.getcid() + " " +  selectedProduct.getpid());
//            nameTextField.setText(selectedProduct.getName());
//            addressTextField.setText(selectedProduct.getAddress());
        }
        else{
            System.out.println("Nothing selected");
        }
    }

    public int getProductId(){
        Orderobj selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if(selectedProduct!=null){
            return selectedProduct.getquantity();
        }
        return -1;
    }
    public Orderobj getProduct(){
        Orderobj selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if(selectedProduct!=null){
            return selectedProduct;
        }
        return null;
    }
}
