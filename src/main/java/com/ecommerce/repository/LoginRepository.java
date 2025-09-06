package com.ecommerce.repository;

import com.ecommerce.model.Login;

public class LoginRepository extends AbstractCsvRepository<Login> {
    
    public LoginRepository() {
        super("data/login.csv", Login.csvHeader(), Login::fromCsv);
    }
}
