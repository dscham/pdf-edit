package at.dslan.pdfedit.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.dslan.pdfedit.model.Document;
import at.dslan.pdfedit.service.DocumentService;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentController {

    private final DocumentService service;

    @PostMapping("/")
    public ResponseEntity<Document> postDocument(@RequestBody Document document) {
        return ResponseEntity.ok(service.saveDocument(document));
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<Document> getDocument(@PathVariable String documentId) {
        return ResponseEntity.ok(service.getDocument(documentId));
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<Document> putDocument(@PathVariable String documentId, @RequestBody Document document) {
        return ResponseEntity.ok(service.updateDocument(documentId, document));
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Document> deleteDocument(@PathVariable String documentId) {
        service.deleteDocument(documentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> convertDocumentToPDF(@RequestBody Document document,
            @RequestParam(value = "file-name", required = false) String fileName) throws IOException {
        return buildPDFResponse(service.convertDocumentToPDF(document, fileName), fileName);
    }

    @GetMapping(value ="/{documentId}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getPDFForDocumentId(@PathVariable String documentId,
            @RequestParam(value = "file-name", required = false) String fileName) throws IOException {
        return buildPDFResponse(service.convertDocumentToPDF(service.getDocument(documentId), fileName), fileName);
    }

    private ResponseEntity<byte[]> buildPDFResponse(byte[] content, String fileName) {
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename="
                                + (StringUtils.isNotBlank(fileName) ? fileName : "default.pdf")
                )
                .body(content);
    }
}
