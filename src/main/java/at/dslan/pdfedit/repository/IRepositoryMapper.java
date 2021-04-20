package at.dslan.pdfedit.repository;

import at.dslan.pdfedit.model.blocks.Block;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.dslan.pdfedit.model.blocks.Generic;
import at.dslan.pdfedit.model.Document;
import at.dslan.pdfedit.repository.entities.BlockEntity;
import at.dslan.pdfedit.repository.entities.DocumentEntity;

@Mapper(implementationName = "RepositoryMapper", componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IRepositoryMapper {
    DocumentEntity convert(Document document);
    Document convert(DocumentEntity documentEntity);

    BlockEntity convert(Block block);
    default String convert(JsonNode jsonNode) {
        if (jsonNode == null) {
            return null;
        }

        ObjectMapper jackson = new ObjectMapper();
        try {
            return jackson.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    Generic convert(BlockEntity blockEntity);
    default JsonNode convert(String json) {
        if (json == null) {
            return null;
        }

        ObjectMapper jackson = new ObjectMapper();
        try {
            return jackson.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
