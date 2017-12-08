/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f17comp1011test2section1;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jwright
 */
public class EditPhoneViewController implements Initializable {

    @FXML    private TextField manufacutureTextField;
    @FXML    private TextField modelTextField;
    @FXML    private TextField memoryTextField;
    @FXML    private TextField colourTextField;
    @FXML    private TextField screenSizeTextField;
    @FXML    private Spinner<Integer> phoneIDSpinner;


    public void loadPhoneInfo() throws SQLException
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/comp1011Test2?useSSL=false", "root", "root");

            statement = conn.prepareStatement("SELECT * FROM phones WHERE phoneID = ?");
            statement.setInt(1, phoneIDSpinner.getValue());
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                manufacutureTextField.setText(resultSet.getString("manufacturer"));
                modelTextField.setText(resultSet.getString("model"));
                memoryTextField.setText(resultSet.getString("memory"));
                colourTextField.setText(resultSet.getString("colour"));
                screenSizeTextField.setText(resultSet.getString("screenSize"));
            }
        }
        catch (SQLException e){
            System.err.println(e);
        }
        finally {
            if (conn != null)
                conn.close();
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manufacutureTextField.setDisable(true);
        modelTextField.setDisable(true);
        memoryTextField.setDisable(true);
        colourTextField.setDisable(true);
        screenSizeTextField.setDisable(true);
        int phoneCount = 1;

        try {
            phoneCount = getPhoneCount();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        SpinnerValueFactory<Integer> phoneIDValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, phoneCount);
        phoneIDSpinner.setValueFactory(phoneIDValueFactory);
        phoneIDSpinner.setEditable(true);
    }

    private int getPhoneCount() throws SQLException {
        int count = 0;
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/comp1011Test2?useSSL=false", "root", "root");

            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(phoneID) FROM phones");

            while (resultSet.next()){
                count = resultSet.getInt(1);
            }
        }
        catch (SQLException e){
            System.err.println(e);
        }
        finally {
            if (conn != null)
                conn.close();
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
        }
        return count;
    }
    
}
