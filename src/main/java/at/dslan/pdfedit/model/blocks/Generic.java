package at.dslan.pdfedit.model.blocks;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import lombok.Data;

@Data
public class Generic extends Block {
    String data;

    public void writeToPDContentStream(PDPageContentStream content) {

    }
}
