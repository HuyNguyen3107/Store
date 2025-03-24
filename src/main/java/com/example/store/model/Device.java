import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.Instant;


@Entity
@Table(name = "devices")
@Data

public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "browser", nullable = false)
    private String browser;

    @NotBlank
    @Size(max = 100)
    @Column(name = "operating_system", nullable = false)
    private String operatingSystem;

    @NotBlank
    @Size(max = 100)
    @Column(name = "device_type", nullable = false)
    private String deviceType;

    @Column(name = "status")
    private Boolean status;

    @Lob
    @NotBlank
    @Column(name = "user_agent", columnDefinition = "TEXT", nullable = false)
    private String userAgent;

    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}