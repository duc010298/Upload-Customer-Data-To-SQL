package util;

import controller.customerDao;
import java.util.ArrayList;
import model.Customer;

public class addManyCustomer {

    public static boolean addMany(ArrayList<Customer> listCus, String[] objConn) {
        boolean status = false;
        customerDao cusDao = new customerDao(objConn);
        for (Customer cus : listCus) {
            status = cusDao.addCustomer(cus);
        }
        return status;
    }
}
