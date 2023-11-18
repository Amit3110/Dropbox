package dropbox.entities.interfaces;

import dropbox.entities.domain.DigitalFiles;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FilesRepoService extends ReactiveMongoRepository<DigitalFiles, String> {
}
