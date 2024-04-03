package scrapper.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "links")
@EqualsAndHashCode(of = {"id"})
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "links_generator")
    @SequenceGenerator(name = "links_generator", sequenceName = "links_sequence", allocationSize = 1)
    private Long id;

    private String url;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime updatedAt;

    @Column(name = "checked_at")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime checkedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    @ToString.Exclude
    private Chat chat;
}
