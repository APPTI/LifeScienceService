package com.littlefrog.respository;

import com.littlefrog.entity.Coupon;
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
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    @Query(value = "SELECT * from coupon  where userid=? order by couponid desc ", nativeQuery = true)
    ArrayList<Coupon> findAllCoupon(Integer userID);

    @Query(value = "SELECT * from coupon  where couponid =? ", nativeQuery = true)
    Optional<Coupon> findOneCoupon(Integer couponID);

    @Transactional
    @Modifying
    @Query(value = "delete from coupon where couponID = ? ", nativeQuery = true)
    void deleteCoupon(Integer couponID);

    /**
     * 删除过期
     */
    @Transactional
    @Modifying
    @Query(value = "delete from coupon where dure_time < now()", nativeQuery = true)
    void deleteCoupons();

}
