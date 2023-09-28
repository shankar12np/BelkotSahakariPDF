package com.example.belkotsaharakibank.Service;

import com.example.belkotsaharakibank.Entity.CheckingAccount;
import com.example.belkotsaharakibank.Repository.CheckingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
public class CheckingAccountService {
    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    public ResponseEntity<String> saveActivities(CheckingAccount accountActivities){
       try {
           checkingAccountRepository.save(accountActivities);
           return ResponseEntity.ok(" Activities saved successfully");
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save activities");
       }
    }

    public List<CheckingAccount> getAllActivities() {
        return checkingAccountRepository.findAll();
    }

    }



