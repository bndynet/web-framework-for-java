/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.bndy.wf.modules.core.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
