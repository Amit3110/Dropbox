package dropbox.application.service;

import dropbox.entities.domain.DigitalFiles;
import dropbox.entities.interfaces.FilesRepoService;
import dropbox.entities.interfaces.FilesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.function.Consumer;

@Service
@Slf4j
public class FilesServiceImpl implements FilesService {
    private FilesRepoService filesRepoService;

    @Autowired
    public FilesServiceImpl(FilesRepoService filesRepoService) {
        this.filesRepoService = filesRepoService;
    }

    @Override
    public Mono<DigitalFiles> processFilePart(FilePart filePart) {
        String uploadDirectory = "C:\\Users\\amita\\Downloads\\DropBoxClone\\src\\main\\resources\\filesStorage";
        Path uploadPath = Paths.get(uploadDirectory);
        Path filePath = uploadPath.resolve(filePart.filename());

        return saveFileMeta(filePart, uploadDirectory.concat("\\").concat(filePart.filename()))
                .map(it -> {
                    filePart.transferTo(filePath.toFile());
                    return it;
                });
    }

    private Mono<DigitalFiles> saveFileMeta(FilePart filePart, String filePath) {
        DigitalFiles files = DigitalFiles.builder()
                .name(filePart.filename())
                .updatedAt(Instant.now().getEpochSecond())
                .path(filePath)
                .build();
        return filesRepoService.save(files);
    }

    @Override
    public Flux<DigitalFiles> getAllFiles() {
        return filesRepoService.findAll();
    }

    @Override
    public Mono<DigitalFiles> getFile(String id) {
        return filesRepoService.findById(id);
    }

    @Override
    public Mono<String> deleteFile(String id) {
        // todo : delete file from storage. using id fetch object & read path -> delete the file
        return filesRepoService.deleteById(id)
                .map(it -> "");
    }
}
