package at.dslan.pdfedit.model;

import lombok.Data;

@Data
public class ImageResponse {
    Integer success;
    ImageMetadata file;
}
