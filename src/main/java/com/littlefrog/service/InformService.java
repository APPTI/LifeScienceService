package com.littlefrog.service;

import com.littlefrog.entity.Inform;
import com.littlefrog.respository.InformRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author DW
 */
@Service("InformService")
@EnableScheduling
public class InformService {
    @Autowired
    private InformRespository informRespository;

    public ArrayList<Inform> getAllInform(Integer userID) {
        return informRespository.findAllInform(userID);
    }

    public void addInform(Integer userID, String content) {
        informRespository.save(new Inform(userID, content));
    }

    public Inform getInformInfo(Integer informID) {
        Optional<Inform> o = informRespository.findOneInform(informID);
        return o.orElse(null);
    }

    public boolean deleteInform(Integer informID) {
        Optional<Inform> o = informRespository.findOneInform(informID);
        if (o.isPresent()) {
            informRespository.deleteInform(informID);
            return true;
        } else {
            return false;
        }
    }

}
