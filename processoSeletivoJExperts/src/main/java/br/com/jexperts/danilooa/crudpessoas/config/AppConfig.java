package br.com.jexperts.danilooa.crudpessoas.config;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AppConfig {

    @PersistenceContext(unitName = "pessoasPersistenceUnit")
    private EntityManager entityManager;

    @Produces
    public EntityManager getEntityManager() {
	return this.entityManager;
    }
}
