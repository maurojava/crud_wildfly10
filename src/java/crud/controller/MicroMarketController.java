package crud.controller;

import crud.controller.util.MobilePageController;
import crud.entities.MicroMarket;
import crud.facade.MicroMarketFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "microMarketController")
@ViewScoped
public class MicroMarketController extends AbstractController<MicroMarket> {

    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isCustomerSetEmpty;

    public MicroMarketController() {
        // Inform the Abstract parent controller of the concrete MicroMarket Entity
        super(MicroMarket.class);
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
        MicroMarket selected = this.getSelected();
        if (selected != null) {
            MicroMarketFacade ejbFacade = (MicroMarketFacade) this.getFacade();
            this.isCustomerSetEmpty = ejbFacade.isCustomerSetEmpty(selected);
        } else {
            this.isCustomerSetEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Customer entities that
     * are retrieved from MicroMarket?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for Customer page
     */
    public String navigateCustomerSet() {
        MicroMarket selected = this.getSelected();
        if (selected != null) {
            MicroMarketFacade ejbFacade = (MicroMarketFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Customer_items", ejbFacade.findCustomerSet(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/customer/index";
    }

}
