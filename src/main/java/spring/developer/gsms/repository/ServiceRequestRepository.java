package spring.developer.gsms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.developer.gsms.model.ServiceRequest;

@Repository
public interface ServiceRequestRepository
        extends JpaRepository<ServiceRequest, Long> {

    Page<ServiceRequest> findByStatus(
            ServiceRequest.RequestStatus status,
            Pageable pageable
    );

    Page<ServiceRequest> findByCitizen_Id(
            Long citizenId,
            Pageable pageable
    );

    Page<ServiceRequest> findByService_Id(
            Long serviceId,
            Pageable pageable
    );

    Page<ServiceRequest> findByCitizen_IdAndStatus(
            Long citizenId,
            ServiceRequest.RequestStatus status,
            Pageable pageable
    );
}
