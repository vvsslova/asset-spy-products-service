package asset.spy.products.service.repository;

import asset.spy.products.service.entity.ProductItemStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemStatusRepository extends JpaRepository<ProductItemStatusEntity, Long>  {
}
