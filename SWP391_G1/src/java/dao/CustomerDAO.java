/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
/**
 *
 * @author namp0
 */
import context.DbContext;
import model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO extends DbContext {

    public void createCustomer(String name, String phone, String address, String email, String image, int accountId) {
        try {
            String sql = "INSERT INTO [Customer] "
                    + "(FullName, "
                    + "Phone, "
                    + "Address, "
                    + "Email, "
                    + "Image, "
                    + "AccountID) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.setString(4, email);
            statement.setString(5, image);
            statement.setInt(6, accountId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        try {
            String sql = "SELECT u.CustomerID, u.FullName, u.Phone, u.Address, u.Email, u.Image, r.RoleName " +
                         "FROM [Customer] u " +
                         "JOIN Account a ON u.AccountID = a.AccountID " +
                         "JOIN Role r ON a.RoleID = r.RoleID";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Customer c = new Customer(
                    rs.getInt("CustomerID"),
                    rs.getString("FullName"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("Image"),
                    rs.getString("RoleName")
                );
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Customer getCustomerById(int id) {
        try {
            String sql = "SELECT u.CustomerID, u.FullName, u.Phone, u.Address, u.Email, u.Image, r.RoleName " +
                         "FROM [Customer] u " +
                         "JOIN Account a ON u.AccountID = a.AccountID " +
                         "JOIN Role r ON a.RoleID = r.RoleID " +
                         "WHERE u.ID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Customer(
                    rs.getInt("CustomerID"),
                    rs.getString("FullName"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("Image"),
                    rs.getString("RoleName")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateCustomer(int id, String name, String phone, String address, String email, String image) {
        try {
            String sql = "UPDATE [Customer] "
                    + "SET FullName = ?,"
                    + " Phone = ?,"
                    + " Address = ?,"
                    + " Email = ?,"
                    + " Image = ?"
                    + " WHERE ID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.setString(4, email);
            statement.setString(5, image);
            statement.setInt(6, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteCustomer(int id) {
        try {
            String sql = "DELETE FROM [Customer] WHERE CustomerID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
