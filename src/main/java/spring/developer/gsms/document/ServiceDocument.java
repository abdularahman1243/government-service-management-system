package spring.developer.gsms.document;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Document(collection = "services")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ServiceDocument {

    @Id
    private String id;

    private String code;
    private String name;
    private String description;
    private Boolean active;

    private LocalDateTime createdAt;
}
