package at.dslan.pdfedit.model.blocks;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BlockType {
    @JsonProperty("paragraph")
    PARAGRAPH,
    @JsonProperty("header")
    HEADER,
    @JsonProperty("image")
    IMAGE,
    @JsonProperty("list")
    LIST,
    @JsonProperty("checklist")
    CHECKLIST,
    @JsonProperty("delimiter")
    DELIMITER,
    @JsonProperty("table")
    TABLE
}
