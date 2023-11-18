package dropbox.web.handler;

import dropbox.entities.domain.DigitalFiles;
import dropbox.entities.interfaces.FilesService;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class FileHandler {
    private FilesService filesService;

    public FileHandler(FilesService filesService) {
        this.filesService = filesService;
    }

    public Mono<ServerResponse> upload(ServerRequest serverRequest) {
        return serverRequest.multipartData()
                .flatMap(parts -> {
                    List<FilePart> fileParts = parts.get("file")
                            .stream()
                            .filter(part -> part instanceof FilePart)
                            .map(part -> (FilePart) part)
                            .collect(Collectors.toList());

                    if (!fileParts.isEmpty()) {
                        FilePart filePart = fileParts.get(0);

                        return filesService.processFilePart(filePart)
                                .flatMap(response -> ServerResponse.ok().bodyValue(response));
                    } else {
                        return ServerResponse.badRequest().bodyValue("File not found in request");
                    }
                });
    }

    public Mono<ServerResponse> getAllFiles(ServerRequest serverRequest) {
        return filesService.getAllFiles()
                .collectList()
                .flatMap(it -> ServerResponse.ok().bodyValue(it));
    }

    public Mono<ServerResponse> getFile(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("fileId");
        return ServerResponse.ok().body(filesService.getFile(id), DigitalFiles.class);
    }

    public Mono<ServerResponse> deleteFile(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("fileId");
        return ServerResponse.ok().body(filesService.deleteFile(id), String.class);
    }
}
