package dropbox.entities.interfaces;

import dropbox.entities.domain.DigitalFiles;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FilesService {
    Mono<DigitalFiles> processFilePart(FilePart filePart);

    Flux<DigitalFiles> getAllFiles();

    Mono<DigitalFiles> getFile(String id);

    Mono<String> deleteFile(String id);
}
