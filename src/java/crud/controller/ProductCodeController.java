package crud.controller;

import crud.controller.util.MobilePageController;
import crud.entities.ProductCode;
import crud.facade.ProductCodeFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "productCodeController")
@ViewScoped
public class ProductCodeController extends AbstractController<ProductCode> {

    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isProductSetEmpty;

    public ProductCodeController() {
        // Inform the Abstract parent controller of the concrete ProductCode Entity
        super(ProductCode.class);
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
        ProductCode selected = this.getSelected();
        if (selected != null) {
            ProductCodeFacade ejbFacade = (ProductCodeFacade) this.getFacade();
            this.isProductSetEmpty = ejbFacade.isProductSetEmpty(selected);
        } else {
            this.isProductSetEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Product entities that are
     * retrieved from ProductCode?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Product page
     */
    public String navigateProductSet() {
        ProductCode selected = this.getSelected();
        if (selected != null) {
            ProductCodeFacade ejbFacade = (ProductCodeFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Product_items", ejbFacade.findProductSet(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/product/index";
    }

}
