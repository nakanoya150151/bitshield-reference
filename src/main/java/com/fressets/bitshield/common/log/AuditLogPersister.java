package com.fressets.bitshield.common.log;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuditLogPersister implements AuditLogDelegate {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void log(AuditLog log) {
		if (entityManager != null) {
			this.entityManager.persist(log);
		}
	}
}
