package spring.developer.gsms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.developer.gsms.document.ServiceDocument;

import java.util.List;
import java.util.Optional;

public interface ServiceMongoRepository
        extends MongoRepository<ServiceDocument, String> {

    Optional<ServiceDocument> findByCode(String code);

    List<ServiceDocument> findByActiveTrue();

    boolean existsByCode(String code);
}
