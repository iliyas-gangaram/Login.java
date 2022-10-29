
package com.example.supplychain;

        import javafx.beans.property.SimpleDoubleProperty;
        import javafx.beans.property.SimpleIntegerProperty;
        import javafx.beans.property.SimpleStringProperty;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;

        import java.io.PipedOutputStream;
        import java.sql.ResultSet;

public class Orderobj {
    // id, name, price
    public SimpleIntegerProperty quantity;
    public SimpleIntegerProperty cid;
    public SimpleIntegerProperty pid;

    public  Orderobj(int quantity, int cid, int pid){
        this.quantity = new SimpleIntegerProperty(quantity);
        this.cid = new SimpleIntegerProperty(cid);
        this.pid = new SimpleIntegerProperty(pid);
    }

    public  int getquantity() { return  quantity.get(); }
    public  int  getcid() { return  cid.get(); }
    public  int getpid() { return  pid.get(); }

    public static ObservableList<Orderobj> getAllProducts(){
        String selectProducts = "SELECT * FROM orders";
        return getProductList(selectProducts);
    }

    public static ObservableList<Orderobj> getProductsByName(String productName){
        String selectProducts = "SELECT * FROM orders";
        return  getProductList(selectProducts);
    }

    private static ObservableList<Orderobj> getProductList(String query){
        DatabaseConnection dbCon = new DatabaseConnection();
        ObservableList<Orderobj> data = FXCollections.observableArrayList();
        try{
            ResultSet rs =  dbCon.getQueryTable(query);
            while(rs.next()){
                data.add(new Orderobj(rs.getInt("quantity"), rs.getInt("customer_id"), rs.getInt("product_id")));
             //System.out.println(rs.getInt("quantity") + " " +
               //         rs.getString("customer_id") + " " );
                       // rs.getDouble("price")
//                );
            }
            rs.close();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return data;
    }



}
