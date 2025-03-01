package asset.spy.products.service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Vendor")
@NoArgsConstructor
@Getter
@Setter
public class VendorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "external_id")
    private UUID externalId;

    @OneToMany(mappedBy = "vendor", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ProductEntity> products;
}