/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.facade;

import crud.entities.Product;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import crud.entities.Product_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import crud.entities.Warehouse;
import crud.entities.ProductCode;
import crud.entities.Manufacturer;
import crud.entities.PurchaseOrder;
import java.util.Set;

/**
 *
 * @author mauro
 */
@Stateless
public class ProductFacade extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "crud_wildfly10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }

    public boolean isWarehouseSetEmpty(Product entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Product> product = cq.from(Product.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(product, entity), cb.isNotEmpty(product.get(Product_.warehouseSet)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Set<Warehouse> findWarehouseSet(Product entity) {
        return this.getMergedEntity(entity).getWarehouseSet();
    }

    public boolean isProductCodeEmpty(Product entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Product> product = cq.from(Product.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(product, entity), cb.isNotNull(product.get(Product_.productCode)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public ProductCode findProductCode(Product entity) {
        return this.getMergedEntity(entity).getProductCode();
    }

    public boolean isManufacturerIdEmpty(Product entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Product> product = cq.from(Product.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(product, entity), cb.isNotNull(product.get(Product_.manufacturerId)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Manufacturer findManufacturerId(Product entity) {
        return this.getMergedEntity(entity).getManufacturerId();
    }

    public boolean isPurchaseOrderSetEmpty(Product entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Product> product = cq.from(Product.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(product, entity), cb.isNotEmpty(product.get(Product_.purchaseOrderSet)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Set<PurchaseOrder> findPurchaseOrderSet(Product entity) {
        return this.getMergedEntity(entity).getPurchaseOrderSet();
    }

    @Override
    public Product findWithParents(Product entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> product = cq.from(Product.class);
        product.fetch(Product_.productCode);
        product.fetch(Product_.manufacturerId);
        product.fetch(Product_.warehouseSet, JoinType.LEFT);
        cq.select(product).where(cb.equal(product, entity));
        try {
            return em.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            return entity;
        }
    }
    
}
