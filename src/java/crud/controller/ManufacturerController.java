package crud.controller;

import crud.controller.util.MobilePageController;
import crud.entities.Manufacturer;
import crud.facade.ManufacturerFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "manufacturerController")
@ViewScoped
public class ManufacturerController extends AbstractController<Manufacturer> {

    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isProductSetEmpty;

    public ManufacturerController() {
        // Inform the Abstract parent controller of the concrete Manufacturer Entity
        super(Manufacturer.class);
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
        Manufacturer selected = this.getSelected();
        if (selected != null) {
            ManufacturerFacade ejbFacade = (ManufacturerFacade) this.getFacade();
            this.isProductSetEmpty = ejbFacade.isProductSetEmpty(selected);
        } else {
            this.isProductSetEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Product entities that are
     * retrieved from Manufacturer?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Product page
     */
    public String navigateProductSet() {
        Manufacturer selected = this.getSelected();
        if (selected != null) {
            ManufacturerFacade ejbFacade = (ManufacturerFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Product_items", ejbFacade.findProductSet(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/product/index";
    }

}
