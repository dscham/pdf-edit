package at.dslan.pdfedit.repository;

import java.util.UUID;

import at.dslan.pdfedit.repository.entities.DocumentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDocumentRepository extends CrudRepository<DocumentEntity, UUID> {
}
