package com.athuull.excel_to_db.Helper;

import com.athuull.excel_to_db.Entity.Customer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String SHEET = "Sheet1";

    public static boolean hasExcelFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }

    public static List<Customer> excelToDatabase(InputStream is){
        try {
            // creates a workbook using the input stream
            XSSFWorkbook workbook = new XSSFWorkbook(is);

            // creates sheet object using the sheet name
            Sheet sheet = workbook.getSheet(SHEET);

            // Creates a row iterator to iterate through the rows
            Iterator<Row> row = sheet.rowIterator();

            // creates a List of type Customers to store all the customer data in the excel
            List<Customer> customers = new ArrayList<Customer>();


            int rowNumber = 0;

            // iterates through the rows
            while (row.hasNext()) {
                Row currentRow = row.next();


                // we don't need header, so we skip
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;

                }
                // create cell iterator to iterate through cells in the row
                Iterator<Cell> cellsInRow = currentRow.cellIterator();

                // create customer object to store each cell content
                Customer customer = new Customer();

                int cellIndex = 0;
                while (cellsInRow.hasNext()) {   //
                    Cell currentCell = cellsInRow.next();  // creates currentCell to hold the curr Cell

                    // switch statement to store data corresponding to the cell in the object
                    switch (cellIndex) {
                        case 0 -> {
                            customer.setId((int) currentCell.getNumericCellValue());
                            break;
                        }
                        case 1 -> {
                            customer.setFirstName(currentCell.getStringCellValue());
                            break;
                        }

                        case 2 -> {
                            customer.setLastName(currentCell.getStringCellValue());
                            break;
                        }

                        case 3 -> {
                            customer.setEmailId(currentCell.getStringCellValue());
                            break;
                        }

                        case 4 -> {
                            customer.setPhone(currentCell.getStringCellValue());
                            break;
                        }

                        default -> {
                        }
                    }

                    cellIndex++;
                }

                customers.add(customer);   // adds the customer to the List of customers


            }

            workbook.close();
            return customers;
        } catch (IOException e) {
            throw new RuntimeException("failed to parse file:" + e.getMessage());
        }

    }
}




