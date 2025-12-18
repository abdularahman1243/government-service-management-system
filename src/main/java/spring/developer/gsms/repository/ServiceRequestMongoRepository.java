package spring.developer.gsms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import spring.developer.gsms.document.ServiceDocument;
import spring.developer.gsms.document.ServiceRequestDocument;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRequestMongoRepository
        extends MongoRepository<ServiceRequestDocument, String> {

    Optional<ServiceRequestDocument> findByRequestNo(String requestNo);

    Page<ServiceRequestDocument> findByCitizenId(Long citizenId, Pageable pageable);

    List<ServiceRequestDocument> findByStatus(String status);

    List<ServiceRequestDocument> findByServiceCode(String serviceCode);

    boolean existsByRequestNo(String requestNo);
}
