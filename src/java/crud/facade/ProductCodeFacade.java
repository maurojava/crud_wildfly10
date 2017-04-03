/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.facade;

import crud.entities.ProductCode;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import crud.entities.ProductCode_;
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
public class ProductCodeFacade extends AbstractFacade<ProductCode> {

    @PersistenceContext(unitName = "crud_wildfly10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductCodeFacade() {
        super(ProductCode.class);
    }

    public boolean isProductSetEmpty(ProductCode entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<ProductCode> productCode = cq.from(ProductCode.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(productCode, entity), cb.isNotEmpty(productCode.get(ProductCode_.productSet)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Set<Product> findProductSet(ProductCode entity) {
        return this.getMergedEntity(entity).getProductSet();
    }
    
}
