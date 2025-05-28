package asset.spy.products.service.repository;

import asset.spy.products.service.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
    Optional<ProductEntity> findByArticle(Long article);

    @Query(value = "SELECT NEXTVAL('product_article_seq')", nativeQuery = true)
    Long getProductArticle();
}