import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.Instant;

// Table user_otps {
//   id int [pk, increment]
//   otp varchar(6) 
//   user_id int  
//   expires datetime
//   created_at timestamptz [default: `now()`]
//   updated_at timestamptz [default: `now()`]
// }

@Entity
@Table(name = "user_otps")
@Data

public class UserOTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 6, max = 6)
    private String otp;

    @NotBlank
    @Size(max = 300)
    @Column(name = "expired")
    private String expired;

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
}
