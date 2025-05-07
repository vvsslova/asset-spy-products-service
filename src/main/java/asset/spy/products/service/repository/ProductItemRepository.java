package asset.spy.products.service.repository;

import asset.spy.products.service.entity.ProductItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItemEntity, UUID> {
    Optional<ProductItemEntity> findById(UUID id);
}
