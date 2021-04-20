package at.dslan.pdfedit.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.pdfbox.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import at.dslan.pdfedit.model.ImageMetadata;
import at.dslan.pdfedit.model.ImageResponse;

@Service
public class ImageService {
    private static final String STORAGE = "images";

    public ImageService() {
        Path directory = Paths.get(STORAGE);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ImageResponse saveImage(MultipartFile file) {
        Path directory = Paths.get(STORAGE);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path destination = Paths.get(directory.toString() + "\\" + fileName);

        try {
            Files.copy(file.getInputStream(), destination,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String resultUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/image/")
                .path(fileName)
                .toUriString();

        ImageResponse result = new ImageResponse();
        result.setSuccess(1);
        ImageMetadata resultMetadata = new ImageMetadata();
        resultMetadata.setUrl(resultUrl);
        result.setFile(resultMetadata);

        return result;
    }

    public void deleteImage(String fileName) throws IOException {
        Path storageDirectory = Paths.get(STORAGE);
        Path storageDestination = Paths.get(storageDirectory.toString() + "\\" + fileName);

        Files.delete(storageDestination);
    }

    public byte[] getImage(String imageName) throws IOException {
        Path destination = Paths.get(STORAGE + "\\" + imageName);

        return IOUtils.toByteArray(destination.toUri().toURL().openStream());
    }
}
