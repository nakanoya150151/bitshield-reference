package com.fressets.bitshield.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.fressets.bitshield.domain.EntityBase;


@NoRepositoryBean
public interface JpaRepositoryBase<T extends EntityBase, ID extends Serializable> extends JpaRepository<T, ID> {

	default void logicalDelete(T entity) {
		entity.setDeleted(true);
	}
}
