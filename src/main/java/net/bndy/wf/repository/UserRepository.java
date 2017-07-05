package net.bndy.wf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.bndy.wf.domain.*;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value="select t.* from user t where t.user_name=:userName", nativeQuery=true)
    List<User> findByUserName(@Param("userName") String userName);
}
