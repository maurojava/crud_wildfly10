package crud.controller;

import crud.controller.util.MobilePageController;
import crud.entities.Customer;
import crud.facade.CustomerFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "customerController")
@ViewScoped
public class CustomerController extends AbstractController<Customer> {

    @Inject
    private MicroMarketController zipController;
    @Inject
    private DiscountCodeController discountCodeController;
    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isPurchaseOrderSetEmpty;

    public CustomerController() {
        // Inform the Abstract parent controller of the concrete Customer Entity
        super(Customer.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        zipController.setSelected(null);
        discountCodeController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsPurchaseOrderSetEmpty();
    }

    public boolean getIsPurchaseOrderSetEmpty() {
        return this.isPurchaseOrderSetEmpty;
    }

    private void setIsPurchaseOrderSetEmpty() {
        Customer selected = this.getSelected();
        if (selected != null) {
            CustomerFacade ejbFacade = (CustomerFacade) this.getFacade();
            this.isPurchaseOrderSetEmpty = ejbFacade.isPurchaseOrderSetEmpty(selected);
        } else {
            this.isPurchaseOrderSetEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of PurchaseOrder entities
     * that are retrieved from Customer?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for PurchaseOrder page
     */
    public String navigatePurchaseOrderSet() {
        Customer selected = this.getSelected();
        if (selected != null) {
            CustomerFacade ejbFacade = (CustomerFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("PurchaseOrder_items", ejbFacade.findPurchaseOrderSet(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/purchaseOrder/index";
    }

    /**
     * Sets the "selected" attribute of the MicroMarket controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareZip(ActionEvent event) {
        Customer selected = this.getSelected();
        if (selected != null && zipController.getSelected() == null) {
            zipController.setSelected(selected.getZip());
        }
    }

    /**
     * Sets the "selected" attribute of the DiscountCode controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareDiscountCode(ActionEvent event) {
        Customer selected = this.getSelected();
        if (selected != null && discountCodeController.getSelected() == null) {
            discountCodeController.setSelected(selected.getDiscountCode());
        }
    }

}
