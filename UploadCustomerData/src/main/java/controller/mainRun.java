/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import view.displayResult;
import view.main;

/**
 *
 * @author �?ỗ Trung �?ức
 */
public class mainRun {

    Thread uploadToSQL;
    Thread displayResult;

    File f;
    String[] objConn;
    Customer cus = new Customer();
    main mainForm;
    displayResult disResult = new displayResult();
    int count = 0;

    public mainRun(File f, String[] objConn, javax.swing.JFrame mainForm) {
        this.f = f;
        this.objConn = objConn;
        this.mainForm = (main) mainForm;
        disResult.setVisible(true);
        mainForm.setLocation(20, 100);
        initUploadToSQL();
        initDisplayResult();
    }

    private void initDisplayResult() {
        displayResult = new Thread() {
            @Override
            public void run() {
                mainForm.setStatus("Processing");
                try {
                    FileInputStream inputStream = new FileInputStream(f);
                    Date tempDate1 = new Date();

                    WorkbookSettings ws = new WorkbookSettings();
                    ws.setEncoding("Cp1252");
                    Workbook workbook = Workbook.getWorkbook(inputStream, ws);
                    Sheet sheet = workbook.getSheet(0);
                    int rows = sheet.getRows();
                    mainForm.initProgressBar(rows);
                    mainForm.setTotal(rows);
                    for (int row = 0; row < rows; row++) {
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
                        synchronized (uploadToSQL) {
                            uploadToSQL.notify();
                        }
                        if (!uploadToSQL.isAlive()) {
                            uploadToSQL.start();
                        }
                        synchronized (this) {
                            wait();
                        }
                        disResult.scrollTable();
                        mainForm.setProgressBar(count);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(mainRun.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IndexOutOfBoundsException ex) {
                    Logger.getLogger(mainRun.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(mainRun.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(mainRun.class.getName()).log(Level.SEVERE, null, ex);
                }
                mainForm.setStatus("Finished");
            }
        };
    }

    private void initUploadToSQL() {
        uploadToSQL = new Thread() {
            @Override
            public void run() {
                customerDao cusDao = new customerDao(objConn);
                while (true) {
                    try {
                        if (cusDao.addCustomer(cus)) {
                            mainForm.setSuccess(++count);
                        }
                        synchronized (displayResult) {
                            displayResult.notify();
                        }
                        synchronized (this) {
                            wait();
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mainRun.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
    }

    public void run() {
        if (!displayResult.isAlive()) {
            displayResult.start();
        }
    }

}
