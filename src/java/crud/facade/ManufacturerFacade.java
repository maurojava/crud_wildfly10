/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.facade;

import crud.entities.Manufacturer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import crud.entities.Manufacturer_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import crud.entities.Product;
import java.util.Set;

/**
 *
 * @author mauro
 */
@Stateless
public class ManufacturerFacade extends AbstractFacade<Manufacturer> {

    @PersistenceContext(unitName = "crud_wildfly10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ManufacturerFacade() {
        super(Manufacturer.class);
    }

    public boolean isProductSetEmpty(Manufacturer entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Manufacturer> manufacturer = cq.from(Manufacturer.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(manufacturer, entity), cb.isNotEmpty(manufacturer.get(Manufacturer_.productSet)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Set<Product> findProductSet(Manufacturer entity) {
        return this.getMergedEntity(entity).getProductSet();
    }
    
}
