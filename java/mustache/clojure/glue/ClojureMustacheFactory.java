package mustache.clojure.glue;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheResolver;
import com.github.mustachejava.MustacheVisitor;
import java.io.File;

/**
 *
 * @author bill
 */
public class ClojureMustacheFactory extends DefaultMustacheFactory {

    public ClojureMustacheFactory() {
        oh = new ClojureObjectHandler();
    }

    public ClojureMustacheFactory(MustacheResolver mustacheResolver) {
        super(mustacheResolver);
        oh = new ClojureObjectHandler();
    }

    public ClojureMustacheFactory(String resourceRoot) {
        super(resourceRoot);
        oh = new ClojureObjectHandler();
    }

    public ClojureMustacheFactory(File fileRoot) {
        super(fileRoot);
        oh = new ClojureObjectHandler();
    }
    
    @Override
    public MustacheVisitor createMustacheVisitor() {
        return new ClojureMustacheVisitor(this);
    }
    
}
