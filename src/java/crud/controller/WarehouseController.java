package crud.controller;

import crud.controller.util.MobilePageController;
import crud.entities.Warehouse;
import crud.facade.WarehouseFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "warehouseController")
@ViewScoped
public class WarehouseController extends AbstractController<Warehouse> {

    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isProductSetEmpty;

    public WarehouseController() {
        // Inform the Abstract parent controller of the concrete Warehouse Entity
        super(Warehouse.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsProductSetEmpty();
    }

    public boolean getIsProductSetEmpty() {
        return this.isProductSetEmpty;
    }

    private void setIsProductSetEmpty() {
        Warehouse selected = this.getSelected();
        if (selected != null) {
            WarehouseFacade ejbFacade = (WarehouseFacade) this.getFacade();
            this.isProductSetEmpty = ejbFacade.isProductSetEmpty(selected);
        } else {
            this.isProductSetEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Product entities that are
     * retrieved from Warehouse?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Product page
     */
    public String navigateProductSet() {
        Warehouse selected = this.getSelected();
        if (selected != null) {
            WarehouseFacade ejbFacade = (WarehouseFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Product_items", ejbFacade.findProductSet(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/product/index";
    }

}
