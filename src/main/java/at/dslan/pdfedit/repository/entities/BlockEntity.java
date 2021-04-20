package at.dslan.pdfedit.repository.entities;

import javax.persistence.Embeddable;

import at.dslan.pdfedit.model.blocks.BlockType;
import lombok.Data;
import org.dom4j.tree.AbstractEntity;

@Data
@Embeddable
public class BlockEntity extends AbstractEntity {
    BlockType type;
    String data;
}
