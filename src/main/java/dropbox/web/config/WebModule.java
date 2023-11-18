package dropbox.web.config;

import dropbox.web.routes.HttpRouter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
@EnableWebFlux
public class WebModule implements WebFluxConfigurer {
    /**
     * routers bean
     *
     * @param httpRouter to use to route http request
     * @return RouterFunction<ServerResponse>
     */
    @Bean
    public RouterFunction<ServerResponse> routers(@NotNull HttpRouter httpRouter) {
        return httpRouter.routes();
    }
}
