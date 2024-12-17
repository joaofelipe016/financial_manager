package com.project.financial_management.interfacesService;

import com.project.financial_management.dto.LoginRequest;
import com.project.financial_management.dto.LoginResponse;

public interface InterfLoginService {

    public LoginResponse login(LoginRequest loginRequest) throws Exception;

}
