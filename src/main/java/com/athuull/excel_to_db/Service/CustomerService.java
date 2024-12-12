package com.athuull.excel_to_db.Service;

import com.athuull.excel_to_db.Entity.Customer;
import com.athuull.excel_to_db.Helper.ExcelHelper;
import com.athuull.excel_to_db.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

   public void save (MultipartFile file ) {
       try {
           List<Customer> customers = ExcelHelper.excelToDatabase(file.getInputStream());
           customerRepository.saveAll(customers);
       } catch (IOException e) {
           throw new RuntimeException("failed to store excel data" + e.getMessage());
       }
   }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

}
