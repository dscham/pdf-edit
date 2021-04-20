package at.dslan.pdfedit.repository.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class DocumentEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column
    private Integer time;

    @ElementCollection
    private List<BlockEntity> blocks;

    @Column
    private String version;
}
