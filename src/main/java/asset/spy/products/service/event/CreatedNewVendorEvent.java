package asset.spy.products.service.event;

public class CreatedNewVendorEvent extends VendorEvent {

    public CreatedNewVendorEvent(String vendorName) {
        super(vendorName);
    }
}
