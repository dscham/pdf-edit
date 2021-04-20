package at.dslan.pdfedit.model;

import java.util.List;
import java.util.UUID;

import at.dslan.pdfedit.model.blocks.Block;
import lombok.Data;

@Data
public class Document {
    private UUID id;
    private Integer time;
    private List<Block> blocks;
    private String version;
}
