/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.facade;

import crud.entities.DiscountCode;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import crud.entities.DiscountCode_;
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
public class DiscountCodeFacade extends AbstractFacade<DiscountCode> {

    @PersistenceContext(unitName = "crud_wildfly10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DiscountCodeFacade() {
        super(DiscountCode.class);
    }

    public boolean isCustomerSetEmpty(DiscountCode entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<DiscountCode> discountCode = cq.from(DiscountCode.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(discountCode, entity), cb.isNotEmpty(discountCode.get(DiscountCode_.customerSet)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Set<Customer> findCustomerSet(DiscountCode entity) {
        return this.getMergedEntity(entity).getCustomerSet();
    }
    
}
