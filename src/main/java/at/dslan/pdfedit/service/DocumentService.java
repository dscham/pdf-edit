package at.dslan.pdfedit.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import at.dslan.pdfedit.model.blocks.Block;
import at.dslan.pdfedit.model.blocks.Generic;
import at.dslan.pdfedit.model.blocks.BlockType;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.dslan.pdfedit.model.Document;
import at.dslan.pdfedit.repository.IDocumentRepository;
import at.dslan.pdfedit.repository.IRepositoryMapper;
import at.dslan.pdfedit.repository.entities.DocumentEntity;

@Service
public class DocumentService {
    private static final String STORAGE = "pdfs";

    private final IRepositoryMapper repositoryMapper;
    private final IDocumentRepository repository;

    public DocumentService(@Autowired IRepositoryMapper repositoryMapper, @Autowired IDocumentRepository repository) {
        this.repositoryMapper = repositoryMapper;
        this.repository = repository;

        Path directory = Paths.get(STORAGE);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Document saveDocument(Document document) {
        DocumentEntity entity = repositoryMapper.convert(document);
        DocumentEntity saved = repository.save(entity);

        return repositoryMapper.convert(saved);
    }

    public Document getDocument(String documentId) {
        DocumentEntity result = repository.findById(UUID.fromString(documentId)).orElseThrow(EntityNotFoundException::new);
        return repositoryMapper.convert(result);
    }

    public Document updateDocument(String documentId, Document document) {
        document.setId(UUID.fromString(documentId));
        return saveDocument(document);
    }

    public void deleteDocument(String documentId) {
        repository.deleteById(UUID.fromString(documentId));
    }

    public byte[] convertDocumentToPDF(Document document, String fileName) throws IOException {
        try(PDDocument pdf = new PDDocument()) {
            PDPage page = new PDPage();
            PDPageContentStream content = new PDPageContentStream(pdf, page);

            for (int i = 0; i < document.getBlocks().size(); i++) {
                Block block = document.getBlocks().get(i);

                if (block.getType() == BlockType.DELIMITER) {
                    pdf.addPage(page);
                    page = new PDPage();
                    content.close();
                    content = new PDPageContentStream(pdf, page);
                }

                block.writeToPDContentStream(content);
            }

            content.close();
            pdf.addPage(page);

            Path file = Paths.get(STORAGE + "\\" + fileName);
            pdf.save(file.toUri().getPath());
            return IOUtils.toByteArray(file.toUri().toURL().openStream());
        }
    }
}
