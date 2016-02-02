package mustache.clojure.glue;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheVisitor;

/**
 *
 * @author bill
 */
public class ClojureMustacheFactory extends DefaultMustacheFactory {

    @Override
    public MustacheVisitor createMustacheVisitor() {
        return new ClojureMustacheVisitor(this);
    }
    
}
