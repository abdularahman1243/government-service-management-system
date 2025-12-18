package spring.developer.gsms.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import spring.developer.gsms.document.ServiceRequestDocument;

import java.util.List;
import java.util.Map;

public interface ServiceRequestReportRepository
        extends MongoRepository<ServiceRequestDocument, String> {

    @Aggregation(pipeline = {
        "{ $group: { _id: '$status', count: { $sum: 1 } } }"
    })
    List<Map<String, Object>> countRequestsByStatus();
}
