package at.dslan.pdfedit.controller;

import java.io.IOException;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import at.dslan.pdfedit.model.ImageResponse;
import at.dslan.pdfedit.service.ImageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController {

    private final ImageService service;

    @PostMapping("/")
    public ResponseEntity<ImageResponse> postImage(@RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok(service.saveImage(file));
    }

    @PostMapping("/by-url")
    public ResponseEntity<ImageResponse> postURL() {
        throw new NotImplementedException("Upload by link not implemented!");
    }

    @GetMapping(
            value = "/{fileName:.+}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public @ResponseBody
    byte[] getImage(@PathVariable String fileName) throws IOException {
        return service.getImage(fileName);
    }

    @DeleteMapping("/{fileName:.+}")
    public ResponseEntity deleteImage(@PathVariable String fileName) throws IOException {
        service.deleteImage(fileName);
        return ResponseEntity.ok().build();
    }
}
