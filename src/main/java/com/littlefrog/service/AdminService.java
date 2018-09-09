package com.littlefrog.service;

import com.littlefrog.respository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service("AdminService")
@EnableScheduling
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public boolean adminLogin(String username,String passwd){
        try {
            String realPass = adminRepository.getPasswdByname(username);
            if(!realPass.isEmpty()&&realPass.equals(passwd)){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
}
