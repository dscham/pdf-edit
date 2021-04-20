package at.dslan.pdfedit.model.blocks;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Data
public class Paragraph extends Block {
    BlockType type;
    JsonNode data;

    public void writeToPDContentStream(PDPageContentStream content) {

    }
}
