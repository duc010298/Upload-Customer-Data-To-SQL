package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import util.DBConn;
import util.MyUtil;
import util.VNCharacterUtils;

public class customerDao {

    Connection conn;

    public customerDao() {
        conn = DBConn.getConnection();
    }

    public boolean addCustomer(Customer cus) {
        String Name = cus.getName();
        String NameS = VNCharacterUtils.removeAccent(Name).toLowerCase();
        int YOB = cus.getYOB();
        String AddressCus = cus.getAddressCus();
        String AddressCusS = VNCharacterUtils.removeAccent(AddressCus).toLowerCase();
        Date DayVisit = cus.getDayVisit();
        Date ExpectedDOB = cus.getExpectedDOB();
        String Result = cus.getResult();
        String Note = cus.getNote();

        String qry = "INSERT INTO Customer VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preSta = conn.prepareStatement(qry);
            preSta.setString(1, Name);
            preSta.setString(2, NameS);
            preSta.setInt(3, YOB);
            preSta.setString(4, AddressCus);
            preSta.setString(5, AddressCusS);

            java.sql.Date sDayVisit = MyUtil.convertUtilToSql(DayVisit);
            preSta.setDate(6, sDayVisit);

            if (ExpectedDOB == null) {
                preSta.setNull(7, java.sql.Types.DATE);
            } else {
                java.sql.Date sExpectedDOB = MyUtil.convertUtilToSql(ExpectedDOB);
                preSta.setDate(7, sExpectedDOB);
            }

            preSta.setString(8, Result);
            if (Note == null) {
                preSta.setNull(9, java.sql.Types.NVARCHAR);
            } else {
                preSta.setString(9, Note);
            }
            preSta.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(customerDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    Logger.getLogger(customerDao.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

}
