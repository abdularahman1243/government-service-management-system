package spring.developer.gsms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.developer.gsms.model.RequestStatusHistory;

import java.util.List;

public interface RequestStatusHistoryRepository
        extends JpaRepository<RequestStatusHistory, Long> {

    List<RequestStatusHistory> findByRequest_IdOrderByChangedAtAsc(
            Long requestId
    );
}
