package asset.spy.products.service.event;

public class UpdatedVendorEvent extends VendorEvent {

    public UpdatedVendorEvent(String vendorName) {
        super(vendorName);
    }
}
