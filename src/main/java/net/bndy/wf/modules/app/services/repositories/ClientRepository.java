package net.bndy.wf.modules.app.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.bndy.wf.modules.app.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
}
