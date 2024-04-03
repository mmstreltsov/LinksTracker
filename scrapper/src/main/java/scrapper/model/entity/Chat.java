package scrapper.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "chat")
@EqualsAndHashCode(of = {"id"})
public class Chat {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Link> links;
}
