package mustache.clojure.glue;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.TemplateContext;
import com.github.mustachejava.codes.IterableCode;
import java.io.Writer;
import java.util.List;

public class ClojureIterableCode extends IterableCode {

    private final String v;
    
    public ClojureIterableCode(TemplateContext tc, DefaultMustacheFactory df, Mustache mustache, String variable, String type) {
        super(tc, df, mustache, variable, type);
        v = variable;
    }

    public ClojureIterableCode(TemplateContext tc, DefaultMustacheFactory df, Mustache mustache, String variable) {
        super(tc, df, mustache, variable);
        v = variable;
    }

    @Override
    public String toString() {
        return v;
    }
    
    @Override
    protected Writer handle(Writer writer, Object resolved, List<Object> scopes) {
        if(resolved != null) {
            if(resolved instanceof Iterable) {
                writer = execute(writer, resolved, scopes);
            }
            else {
                writer = super.handle(writer, resolved, scopes);
            }
        }
        return writer;
    }    
    
}
