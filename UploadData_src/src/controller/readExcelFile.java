package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import model.Customer;
import util.MyUtil;
import util.addManyCustomer;
import view.displayResult;

public class readExcelFile {

    public static boolean readFile(File f, String[] objConn, javax.swing.JFrame index) {
        ArrayList<Customer> listCus;
        listCus = new ArrayList<>();
        displayResult disResult = new displayResult();
        try {
            FileInputStream inputStream = new FileInputStream(f);
            Date tempDate1 = new Date();

            WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("Cp1252");
            Workbook workbook = Workbook.getWorkbook(inputStream, ws);
            Sheet sheet = workbook.getSheet(0);
            int rows = sheet.getRows();
            for (int row = 0; row < rows; row++) {
                Customer cus = new Customer();
                for (int col = 0; col < 7; col++) {
                    Cell cell = sheet.getCell(col, row);
                    String content = cell.getContents();
                    switch (col) {
                        case 0:
                            if (content.equals("")) {
                                cus.setDayVisit(tempDate1);
                            } else {
                                Date temp = MyUtil.convertStrToDate(content);
                                if (temp == null) {
                                    cus.setDayVisit(tempDate1);
                                } else {
                                    tempDate1 = temp;
                                    cus.setDayVisit(temp);
                                }
                            }
                            break;
                        case 1:
                            if (content.equals("")) {
                                cus.setName("Không có tên");
                            } else {
                                cus.setName(content);
                            }
                            break;
                        case 2:
                            if (content.equals("")) {
                                cus.setYOB(0);
                            } else {
                                Date date = new Date();
                                Calendar calendar = new GregorianCalendar();
                                calendar.setTime(date);
                                int year = calendar.get(Calendar.YEAR);
                                int age = 0;
                                try {
                                    age = Integer.parseInt(content);
                                } catch (NumberFormatException ex) {
                                    cus.setYOB(0);
                                }
                                cus.setYOB(year - age);
                            }
                            break;

                        case 3:
                            if (content.equals("")) {
                                cus.setAddressCus("Không có địa chỉ");
                            } else {
                                cus.setAddressCus(content);
                            }
                            break;
                        case 4:
                            if (content.equals("")) {
                                cus.setExpectedDOB(null);
                            } else {
                                Date temp = MyUtil.convertStrToDate(content);
                                if (temp == null) {
                                    cus.setExpectedDOB(null);
                                } else {
                                    cus.setExpectedDOB(temp);
                                }
                            }
                            break;
                        case 5:
                            if (content.equals("")) {
                                cus.setResult("Không có kết quả");
                            } else {
                                cus.setResult(content);
                            }
                            break;
                        case 6:
                            if (content.equals("")) {
                                cus.setNote(null);
                            } else {
                                cus.setNote(content);
                            }
                            break;
                    }
                }
                disResult.addCustomer(cus);
                listCus.add(cus);
            }
        } catch (IOException | IndexOutOfBoundsException | NumberFormatException | BiffException ex) {
            Logger.getLogger(readExcelFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean status =  addManyCustomer.addMany(listCus, objConn);
        disResult.setVisible(true);
        index.setLocation(20, 100);
        return status;
    }

}
