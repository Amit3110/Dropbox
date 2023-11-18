package dropbox.web.routes;

import dropbox.web.handler.FileHandler;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.inject.Named;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Named
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HttpRouter {
    private final FileHandler fileHandler;

    public HttpRouter(FileHandler userHandler) {
        this.fileHandler = userHandler;
    }

    public RouterFunction<ServerResponse> routes(){
        return RouterFunctions.route()
                .POST("/files/upload", accept(MULTIPART_FORM_DATA), fileHandler::upload)
                .GET("/files", accept(APPLICATION_JSON), fileHandler::getAllFiles)
                .GET("/files/{fileId}", accept(APPLICATION_JSON), fileHandler::getFile)
                .DELETE("/files/{fileId}", accept(APPLICATION_JSON), fileHandler::deleteFile)
//                .PUT("/files/{fileId}", accept(APPLICATION_JSON), fileHandler::updateUser)
                .resources("/**", new ClassPathResource("static/"))
                .build();
    }
}