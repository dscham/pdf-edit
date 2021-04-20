package at.dslan.pdfedit.model.blocks;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        defaultImpl = Generic.class
)
@JsonSubTypes({
        @Type(value = Paragraph.class, name = "paragraph"),
        @Type(value = Header.class, name = "header"),
        @Type(value = Image.class, name = "iamge"),
        @Type(value = List.class, name = "list"),
        @Type(value = Checklist.class, name = "checklist"),
        @Type(value = Delimiter.class, name = "delimiter"),
        @Type(value = Table.class, name = "table")
})
public abstract class Block {
    private BlockType type;
    public abstract void writeToPDContentStream(PDPageContentStream content);
}
