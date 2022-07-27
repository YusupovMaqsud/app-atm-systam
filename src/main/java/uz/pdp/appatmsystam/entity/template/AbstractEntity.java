package uz.pdp.appatmsystam.entity.template;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import uz.pdp.appatmsystam.entity.User;

import javax.persistence.*;
import java.security.Timestamp;

@MappedSuperclass
@Data
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;

    @JoinColumn(updatable = false) //class tipida JoinColumn bo'ladi
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy; //kim add qildi

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy; //kim edit qildi




}
