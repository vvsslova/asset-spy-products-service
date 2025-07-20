package asset.spy.products.service.event;

public class DeletedVendorEvent extends VendorEvent {

    public DeletedVendorEvent(String vendorName) {
        super(vendorName);
    }
}
