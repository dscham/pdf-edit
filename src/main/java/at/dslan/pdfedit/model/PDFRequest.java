package at.dslan.pdfedit.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class PDFRequest {
    private String filename;
    private JsonNode content;
}
