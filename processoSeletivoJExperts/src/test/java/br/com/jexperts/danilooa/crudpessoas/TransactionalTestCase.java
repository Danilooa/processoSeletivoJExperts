package br.com.jexperts.danilooa.crudpessoas;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;

public abstract class TransactionalTestCase {

    @Inject
    private UserTransaction utx;

    protected abstract EntityManager getEntityManager();

    @Before
    public void startTransaction() throws Exception {
	utx.begin();
	try {
	    getEntityManager().joinTransaction();
	} catch (Exception e) {
	    getEntityManager().joinTransaction();
	}
    }

    @After
    public void endTransaction() throws Exception {
	utx.rollback();
    }

    public static JavaArchive createDeployment() {
	return ShrinkWrap.create(JavaArchive.class);
    }

}