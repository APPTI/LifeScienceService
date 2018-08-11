package com.littlefrog.respository;

import com.littlefrog.entity.Inform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;


/**
 * @author DW
 */
@Repository
public interface InformRespository extends JpaRepository<Inform, Integer> {
    @Query(value = "SELECT * from inform a where a.userid=? ", nativeQuery = true)
    ArrayList<Inform> findAllInform(Integer userID);

    @Query(value = "SELECT * from inform a where a.informid =? ", nativeQuery = true)
    Optional<Inform> findOneInform(Integer informID);

    @Transactional
    @Modifying
    @Query(value = "delete from inform where informID = ? ", nativeQuery = true)
    void deleteInform(Integer informID);

}
