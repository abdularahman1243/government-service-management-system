package spring.developer.gsms.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "service_requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ServiceRequestDocument {

    @Id
    private String id;

    private String requestNo;

    private String serviceCode;
    private Long citizenId;   // from MySQL

    private String status;    // SUBMITTED, IN_REVIEW, APPROVED, ...

    private String details;

    private List<StatusHistory> history;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class StatusHistory {
        private String fromStatus;
        private String toStatus;
        private Long changedBy;
        private LocalDateTime changedAt;
        private String comment;
    }
}
