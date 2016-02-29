package mustache.clojure.glue;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheResolver;
import com.github.mustachejava.MustacheVisitor;
import com.github.mustachejava.resolver.ClasspathResolver;
import com.github.mustachejava.resolver.FileSystemResolver;
import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author bill
 */
public class ClojureMustacheFactory extends DefaultMustacheFactory {

    /**
     * Finds resources based on classpath root
     */
    public ClojureMustacheFactory() {
        super(new ClasspathResolver());
        oh = new ClojureObjectHandler();
    }

    /**
     * Finds resources based on provided root in classpath
     * @param resourceRoot Location in classpath to resolve resources
     */
    public ClojureMustacheFactory(String resourceRoot) {
        super(new ClasspathResolver(resourceRoot));
        oh = new ClojureObjectHandler();
    }

    /**
     * Resolve files based on provided location on disk
     * @param fileRoot Location on file system to find templates
     */
    public ClojureMustacheFactory(File fileRoot) {
        super(new FileSystemResolver(fileRoot));
        oh = new ClojureObjectHandler();
    }
    
    /**
     * Resolve files based on provided location on disk
     * @param fileRoot Location on file system to find templates
     */
    public ClojureMustacheFactory(Path fileRoot) {
        super(new FileSystemResolver(fileRoot.toFile()));
        oh = new ClojureObjectHandler();
    }
    
    /**
     * Finds resources based upon custom resolver
     * @param mustacheResolver 
     */
    public ClojureMustacheFactory(MustacheResolver mustacheResolver) {
        super(mustacheResolver);
        oh = new ClojureObjectHandler();
    }

    @Override
    public MustacheVisitor createMustacheVisitor() {
        return new ClojureMustacheVisitor(this);
    }
    
}
