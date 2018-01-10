/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services.repositories;

import net.bndy.wf.modules.core.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query(
        nativeQuery = true,
        value = "SELECT * FROM core_user WHERE username LIKE %:keywords% ORDER BY ?#{#pageable}",
        countQuery = "SELECT count(*) FROM core_user WHERE username LIKE %:keywords%"
    )
    Page<User> search(@Param(value = "keywords") String keywords, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM core_user_role WHERE user_id = :userId", nativeQuery = true)
    void deleteRolesByUserId(@Param(value = "userId") long userId);
}
