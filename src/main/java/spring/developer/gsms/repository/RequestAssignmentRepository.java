package spring.developer.gsms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.developer.gsms.model.RequestAssignment;

import java.util.Optional;

public interface RequestAssignmentRepository
        extends JpaRepository<RequestAssignment, Long> {

    Optional<RequestAssignment> findByRequest_Id(Long requestId);
}
