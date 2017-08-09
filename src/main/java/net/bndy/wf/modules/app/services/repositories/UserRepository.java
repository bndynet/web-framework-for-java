package net.bndy.wf.modules.app.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.bndy.wf.modules.app.models.*;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value="select t.* from app_user t where t.user_name=:userName", nativeQuery=true)
    List<User> findByUserName(@Param("userName") String userName);
}
