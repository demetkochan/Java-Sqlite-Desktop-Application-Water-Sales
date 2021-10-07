/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;


public class DB {
    
    private final String driver = "org.sqlite.JDBC";
    private final String url ="jdbc:sqlite:db/proje.db";
    
    
    private Connection conn;
    private PreparedStatement pre;


    public DB(){
        
        try {
            Class.forName(driver); //ilgili kütüphaneyi ayağa kaldırır
            conn = DriverManager.getConnection(url); //driverın yöneticisine bağlanarak getConnection ile bağlantı sağlanır.
            System.out.println("Connection Success");
            
            
        } catch (Exception e) {
            System.err.println("Connection Error: " + e);
        }
    }
    
    //müşteri arama
    public DefaultTableModel searchCustomer(String name,String surname){
        DefaultTableModel dt = new DefaultTableModel();
        
        dt.addColumn("Cid");
        dt.addColumn("Adı");
        dt.addColumn("Soyadı");
        dt.addColumn("Telefon");
        dt.addColumn("Adres");
        
       
        try {
           
            String sql = "select * from customer where name= ? and surname= ? ";
            pre = conn.prepareStatement(sql);
            pre.setString(1, name);
            pre.setString(2, surname);
            ResultSet rs = pre.executeQuery();
            
            
            while(rs.next()){
                int cid = rs.getInt("cid");
                String cname = rs.getString("name");
                String csurname = rs.getString("surname");
                String number = rs.getString("number");
                String address = rs.getString("address");
                
                Object [] row = {cid,cname,csurname,number,address };
                dt.addRow(row);
            }
            
        } catch (Exception e) {
            System.err.println("CustomerSearch: " + e);
        }
        
        
        return dt;
    }

    //list customer table
    public DefaultTableModel allCustomer(){
         DefaultTableModel cdtm = new DefaultTableModel();
         
        cdtm.addColumn("Cid");
        cdtm.addColumn("Adı");
        cdtm.addColumn("Soyadı");
        cdtm.addColumn("Telefon");
        cdtm.addColumn("Adres");
        
        try {
            String sql = "select * from customer";
            pre = conn.prepareStatement(sql);
            
            
            ResultSet rs = pre.executeQuery();
            
            while(rs.next()){
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String number = rs.getString("number");
                String address = rs.getString("address");
                
                Object [] row = {cid,name,surname,number,address };
                cdtm.addRow(row);
            }
        } catch (Exception e) {
            System.err.println("allCustomer: " + e);
        }
         
        return cdtm;
    }
    
    //list customer orders
    public DefaultTableModel allOrders(){
         DefaultTableModel odtm = new DefaultTableModel();
         
         
        odtm.addColumn("cid");
        odtm.addColumn("Müşteri Adı");
        odtm.addColumn("Müşteri Soyadı");
        odtm.addColumn("Durum");
        odtm.addColumn("Adres");
        odtm.addColumn("Tutar");
        
        try {
            String sql = "select * from orders";
            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            
            
            while(rs.next()){
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String state = rs.getString("state");
                String address = rs.getString("address");
                String total = rs.getString("total");
                
                Object [] row = {cid,name,surname,state,address, total };
                odtm.addRow(row);
                
            }
            
        } catch (Exception e) {
            System.err.println("allOrders: " + e);
            
        }
         return odtm;
    }
    //customer kaydı
    
    public int customerInsert(String name, String surname,String number,String address){
        int status =0;
        
        try {
            String sql = " insert into customer values ( null, ?, ?, ?, ? )";
            pre = conn.prepareStatement(sql);
            pre.setString(1, name);
            pre.setString(2, surname);
            pre.setString(3, number);
            pre.setString(4, address);
            status = pre.executeUpdate();
            
            
        } catch (Exception e) {
             System.err.println("CustomerInsert Error: " + e);
             if (e.toString().contains("SQLITE_CONSTRAINT_UNIQUE")){ //aynı üründe girilirse hata verir
                status = -1;
            }
             
        }
        
        
        
        
        
        
        return status;
        
    }
    
    public int customerUpdate(String name, String surname, String number, String address,int customerid){

        int status = 0;
        try {
             String sql = "update customer set name = ?, surname = ?, number = ?, address = ? where cid = ? ";
             pre = conn.prepareStatement(sql);
             pre.setString(1, name);
             pre.setString(2, surname);
             pre.setString(3, number);
             pre.setString(4, address);
             pre.setInt(5, customerid);
             status = pre.executeUpdate();
             
        } catch (Exception e) {
            System.err.println("CustomerUpdate Error: " + e);
            if (e.toString().contains("SQLITE_CONSTRAINT_UNIQUE")){ //aynı üründe girilirse hata verir
                status = -1;
            }
        }
        
        
        
        return status;
        
        
        
    }
    
    public int customerDelete(int customerid){
        int status = 0;
        
        try {
            String sql = "delete from customer where cid = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, customerid);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("Customer Delete: " + e);
        }
        
        
        return status;
        
    }
    
    public int orderInsert(String name,String surname, String address, String total){
        int status = 0;
       
        
        try {
            String sql = " insert into orders values ( null, ?, ?, 'Hazırlanıyor' , ? ,?)";
            pre = conn.prepareStatement(sql);
            pre.setString(1, name);
            pre.setString(2, surname);
            pre.setString(3, address);
            pre.setString(4, total);
            status = pre.executeUpdate();
            
            
             
            
        } catch (Exception e) {
            System.err.println("Order Update: " + e);
        }
        
      
        
        
        
        
        return status;
        
    }
    
    public int orderUpdate(int customerid){
        int status = 0;
        
        try {
            String sql = "update orders set state ='Yola Çıktı' where cid = ? ";
            pre = conn.prepareStatement(sql);
            pre.setInt(1,customerid);
            status = pre.executeUpdate();
            
            
            
            
        } catch (Exception e) {
            System.err.println("Yola çıktı Update: " + e);
        }
        
        
        return status;
        
    }
    
    
    public int orderUpdate2(int customerid){
        int status = 0;
        
        try {
            String sql = "update orders set state ='Teslim Edildi' where cid = ? ";
            pre = conn.prepareStatement(sql);
            pre.setInt(1,customerid);
            status = pre.executeUpdate();
            
        } catch (Exception e) {
            System.err.println("Teslim Edildi Update: " + e);
        }
            
        
        return status;
    }
    
    public int ordersDelete(int customerid){
        int status = 0;
        
        try {
            String sql = "delete from orders where cid = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1,customerid);
            status = pre.executeUpdate();
            
        } catch (Exception e) {
            System.err.println("Order Delete: " + e);
        }
        
        return status;
    }
    
    public int allOrdersDelete(){
        int status = 0;
        try {
             String sql = "delete from orders ";
             pre = conn.prepareStatement(sql);
             status = pre.executeUpdate();
             
        } catch (Exception e) {
            System.err.println("All Orders Delete: " + e);
        }
        
        return status;
    }
    
    public DefaultTableModel listOrder(){
        DefaultTableModel dtm = new DefaultTableModel();
        
        dtm.addColumn("Cid");
        dtm.addColumn("Müşteri Adı");
        dtm.addColumn("Müşteri Soyadı");
        dtm.addColumn("Durum");
        dtm.addColumn("Adres");
        dtm.addColumn("Tutar");
        
        try {
            String sql = "select * from orders where state = 'Hazırlanıyor'";
            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            
             while(rs.next()){
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String state = rs.getString("state");
                String address = rs.getString("address");
                String total = rs.getString("total");
                
                Object [] row = {cid,name,surname,state,address, total };
                dtm.addRow(row);
                 
             }
            
            
            
            
        } catch (Exception e) {
            
            System.err.println("allToday Orders: " + e);
        }
        
        
        
        
        return dtm;
        
        
    }
    
    
       
        
     public void close(){
        
        try {
            if( conn != null && !conn.isClosed()){ //eğer db açıksa kapatılır
                conn.close();
                
            }
            if ( pre != null ){ //pre de conn dan oluşan bir nesne o yüzden conn kapatılırsa buda kapatılır.
                pre.close();
            }
        } catch (Exception e) {
            System.err.println("Close error: " + e);
        }
        
    }   
        
        
        
        
        
        
        
        
        
        
        
        
    }


















