package moviebuddy.data;

import moviebuddy.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;

public abstract class AbstractMetadataResourceMovieReader implements ResourceLoaderAware {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private String metadata;
    private ResourceLoader resourceLoader;

    public String getMetadata() {
        return metadata;
    }

    @Value("${movie.metadata}")
    public void setMetadata(String metadata) {
        this.metadata = Objects.requireNonNull(metadata, "metadata is a required value.");
    }

    public URL getMetadataUrl(){
        String location = getMetadata();
        if(location.startsWith("file:")){
            // file Url 처리
        }else if (location.startsWith("http:")){
            // http URL처리
        }
        return ClassLoader.getSystemResource(location);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource getMetadataResource(){
        return resourceLoader.getResource(getMetadata());
    }
    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        // ClassLoader.getSystemResource() >> 클래스패스 상의자원만 처리가능
        // file, http, ftp 등의 프로토콜사용시?

        Resource resource = getMetadataResource();
        if(resource.exists() == false){
            throw new FileNotFoundException(metadata);
        }
        if( resource.isReadable() == false){
            throw new ApplicationException(String.format("cannot read to metadata. [%s]", metadata));
        }

        log.info(resource + " is ready.");
    }

    @PreDestroy
    public void destroy() throws Exception {
        log.info("Destroyed bean");
    }
}
