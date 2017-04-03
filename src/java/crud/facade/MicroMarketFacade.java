/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.facade;

import crud.entities.MicroMarket;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import crud.entities.MicroMarket_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import crud.entities.Customer;
import java.util.Set;

/**
 *
 * @author mauro
 */
@Stateless
public class MicroMarketFacade extends AbstractFacade<MicroMarket> {

    @PersistenceContext(unitName = "crud_wildfly10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MicroMarketFacade() {
        super(MicroMarket.class);
    }

    public boolean isCustomerSetEmpty(MicroMarket entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<MicroMarket> microMarket = cq.from(MicroMarket.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(microMarket, entity), cb.isNotEmpty(microMarket.get(MicroMarket_.customerSet)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Set<Customer> findCustomerSet(MicroMarket entity) {
        return this.getMergedEntity(entity).getCustomerSet();
    }
    
}
