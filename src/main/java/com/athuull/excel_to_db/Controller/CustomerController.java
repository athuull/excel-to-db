package com.athuull.excel_to_db.Controller;

import com.athuull.excel_to_db.Entity.Customer;
import com.athuull.excel_to_db.Helper.ExcelHelper;
import com.athuull.excel_to_db.Service.CustomerService;
import com.athuull.excel_to_db.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam ("file")MultipartFile file) {
        String message = "";

        if(ExcelHelper.hasExcelFormat(file)) {
            try {
                customerService.save(file);
                message = "File has uploaded successfully" + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage (message));
            } catch (Exception e) {
                message = "Could not upload file" + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message)) ;
            }
        }

        message = "Please upload an excel file";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllTutorials() {
        try {
            List<Customer> tutorials = customerService.getAllCustomers();

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public void deleteAllCustomers() {
        customerService.deleteAllCustomers();
    }
}
