/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services.repositories;

import net.bndy.wf.modules.core.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    File findByUuid(String uuid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE core_file SET is_ref = 1 WHERE id IN (:ids)", nativeQuery = true)
    void setRef(@Param("ids") List<Long> ids);

    @Query(value = "SELECT * FROM core_file WHERE id IN (:ids)", nativeQuery = true)
    List<File> findByIds(@Param("ids") List<Long> ids);
}
