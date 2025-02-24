package asset.spy.products.service.services;

import asset.spy.products.service.dto.VendorDTO;
import asset.spy.products.service.entity.Vendor;
import asset.spy.products.service.exception.EntityNotSavedException;
import asset.spy.products.service.mapper.VendorMapper;
import asset.spy.products.service.repositories.VendorRepository;
import asset.spy.products.service.util.hashing.IDHasher;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public List<VendorDTO> getVendors(int page, int size) {

        log.info("Get vendors from page {} of size {}", page, size);

        return vendorRepository
                .findAll(PageRequest.of(page, size))
                .getContent()
                .stream()
                .map(vendorMapper::toVendorDTO)
                .collect(Collectors.toList());
    }

    public VendorDTO getVendorById(Long id) {
        log.info("'getVendorById' was called with id {}", id);

        Vendor vendor = vendorRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("This vendor does not exist"));

        return vendorMapper.toVendorDTO(vendor);
    }

    @Transactional
    public VendorDTO saveVendor(VendorDTO vendor) {
        log.info("Received vendor to save : {}", vendor);

        try {
            Vendor vendorToSave = vendorMapper.toVendor(vendor);
            log.info("Vendor to save : {}", vendorToSave);

            return vendorMapper.toVendorDTO(vendorRepository.save(vendorToSave));
        } catch (DataIntegrityViolationException e) {
            log.error("Error saving vendor", e);
            throw new EntityNotSavedException("This vendor already exists", e.getCause());
        }
    }

    @Transactional
    public VendorDTO updateVendor(VendorDTO vendor) {
        log.info("Received vendor to update : {}", vendor);

        Vendor vendorToUpdate = vendorRepository
                .findById(IDHasher.hashId(vendor.getId()))
                .orElseThrow(() -> new EntityNotFoundException("This vendor does not exist"));

        vendorToUpdate.setName(vendor.getName());
        vendorToUpdate.setCountry(vendor.getCountry());
        log.info("Updating vendor : {}", vendorToUpdate);

        return vendorMapper.toVendorDTO(vendorToUpdate);
    }

    @Transactional
    public String deleteVendor(Long id) {
        log.info("Received id to delete : {}", id);

        Optional<Vendor> vendorToDelete = vendorRepository.findById(IDHasher.hashId(id));
        if (vendorToDelete.isEmpty()) {
            throw new EntityNotFoundException("This vendor does not exist");
        }

        vendorRepository.deleteById(IDHasher.hashId(id));
        log.info("Vendor with id {} was deleted", id);
        return "Vendor with id " + id + " was deleted";
    }
}
