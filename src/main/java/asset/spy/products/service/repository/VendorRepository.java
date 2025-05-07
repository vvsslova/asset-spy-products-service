package asset.spy.products.service.repository;

import asset.spy.products.service.entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Long>, JpaSpecificationExecutor<VendorEntity> {
    Optional<VendorEntity> findByExternalId(UUID id);

    @Query("SELECT v.name FROM VendorEntity v")
    List<String> findAllVendorNames();
}