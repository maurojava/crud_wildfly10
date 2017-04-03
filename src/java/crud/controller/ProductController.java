package crud.controller;

import crud.controller.util.MobilePageController;
import crud.entities.Product;
import crud.facade.ProductFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "productController")
@ViewScoped
public class ProductController extends AbstractController<Product> {

    @Inject
    private ProductCodeController productCodeController;
    @Inject
    private ManufacturerController manufacturerIdController;
    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isWarehouseSetEmpty;
    private boolean isPurchaseOrderSetEmpty;

    public ProductController() {
        // Inform the Abstract parent controller of the concrete Product Entity
        super(Product.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        productCodeController.setSelected(null);
        manufacturerIdController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsWarehouseSetEmpty();
        this.setIsPurchaseOrderSetEmpty();
    }

    public boolean getIsWarehouseSetEmpty() {
        return this.isWarehouseSetEmpty;
    }

    private void setIsWarehouseSetEmpty() {
        Product selected = this.getSelected();
        if (selected != null) {
            ProductFacade ejbFacade = (ProductFacade) this.getFacade();
            this.isWarehouseSetEmpty = ejbFacade.isWarehouseSetEmpty(selected);
        } else {
            this.isWarehouseSetEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Warehouse entities that
     * are retrieved from Product?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Warehouse page
     */
    public String navigateWarehouseSet() {
        Product selected = this.getSelected();
        if (selected != null) {
            ProductFacade ejbFacade = (ProductFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Warehouse_items", ejbFacade.findWarehouseSet(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/warehouse/index";
    }

    /**
     * Sets the "selected" attribute of the ProductCode controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareProductCode(ActionEvent event) {
        Product selected = this.getSelected();
        if (selected != null && productCodeController.getSelected() == null) {
            productCodeController.setSelected(selected.getProductCode());
        }
    }

    /**
     * Sets the "selected" attribute of the Manufacturer controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareManufacturerId(ActionEvent event) {
        Product selected = this.getSelected();
        if (selected != null && manufacturerIdController.getSelected() == null) {
            manufacturerIdController.setSelected(selected.getManufacturerId());
        }
    }

    public boolean getIsPurchaseOrderSetEmpty() {
        return this.isPurchaseOrderSetEmpty;
    }

    private void setIsPurchaseOrderSetEmpty() {
        Product selected = this.getSelected();
        if (selected != null) {
            ProductFacade ejbFacade = (ProductFacade) this.getFacade();
            this.isPurchaseOrderSetEmpty = ejbFacade.isPurchaseOrderSetEmpty(selected);
        } else {
            this.isPurchaseOrderSetEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of PurchaseOrder entities
     * that are retrieved from Product?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for PurchaseOrder page
     */
    public String navigatePurchaseOrderSet() {
        Product selected = this.getSelected();
        if (selected != null) {
            ProductFacade ejbFacade = (ProductFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("PurchaseOrder_items", ejbFacade.findPurchaseOrderSet(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/purchaseOrder/index";
    }

}
