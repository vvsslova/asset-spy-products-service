package asset.spy.products.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "product_item_status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductItemStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "status_transition_time", nullable = false)
    private OffsetDateTime statusTransitionTime;

    @ManyToOne
    @JoinColumn(name = "product_item_id", referencedColumnName = "id", nullable = false)
    private ProductItemEntity productItem;
}
