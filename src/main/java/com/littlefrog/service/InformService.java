package com.littlefrog.service;

import com.littlefrog.common.Category;
import com.littlefrog.entity.Inform;
import com.littlefrog.entity.Loggers;
import com.littlefrog.respository.InformRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

/**
 * @author DW
 */
@Service("InformService")
public class InformService {
    @Autowired
    private InformRespository informRespository;

    public ArrayList<Inform> getAllInform(Integer userID) {
        return informRespository.findAllInform(userID);
    }

    public boolean addInform(Integer userID, String content, Category category, Integer returnID) {
        //由于关联等原因抛出异常
        try {
            informRespository.save(new Inform(userID, content, category, returnID));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Inform getInformInfo(Integer informID) {
        Optional<Inform> o = informRespository.findOneInform(informID);
        return o.orElse(null);
    }

    public boolean deleteInform(Integer informID) {
        Optional<Inform> o = informRespository.findOneInform(informID);
        if (o.isPresent() && o.get().getUserID() != -1) {
            informRespository.deleteInform(informID);
            return true;
        } else {
            return false;
        }
    }

    public void deleteInformByCategory(Category category, int returnID) {
        informRespository.deleteByCategoryID(category.ordinal(), returnID);
    }

    public void deleteAllInform(int userID) {
        informRespository.deleteAllInform(userID);
    }

    /**
     * 按时清理工具
     */
    @Scheduled(cron = "6 0 0 * * ? ")
    public void delete() {
        Calendar now = Calendar.getInstance();
        int days = now.get(Calendar.DAY_OF_YEAR);
        now.set(Calendar.DAY_OF_YEAR, days < 7 ? days : days - 7);
        informRespository.deleteInforms(now);
        Loggers.getLogger().info("Delete  inform");
    }
}
