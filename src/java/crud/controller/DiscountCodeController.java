package crud.controller;

import crud.controller.util.MobilePageController;
import crud.entities.DiscountCode;
import crud.facade.DiscountCodeFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "discountCodeController")
@ViewScoped
public class DiscountCodeController extends AbstractController<DiscountCode> {

    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isCustomerSetEmpty;

    public DiscountCodeController() {
        // Inform the Abstract parent controller of the concrete DiscountCode Entity
        super(DiscountCode.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsCustomerSetEmpty();
    }

    public boolean getIsCustomerSetEmpty() {
        return this.isCustomerSetEmpty;
    }

    private void setIsCustomerSetEmpty() {
        DiscountCode selected = this.getSelected();
        if (selected != null) {
            DiscountCodeFacade ejbFacade = (DiscountCodeFacade) this.getFacade();
            this.isCustomerSetEmpty = ejbFacade.isCustomerSetEmpty(selected);
        } else {
            this.isCustomerSetEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Customer entities that
     * are retrieved from DiscountCode?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for Customer page
     */
    public String navigateCustomerSet() {
        DiscountCode selected = this.getSelected();
        if (selected != null) {
            DiscountCodeFacade ejbFacade = (DiscountCodeFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Customer_items", ejbFacade.findCustomerSet(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/customer/index";
    }

}
