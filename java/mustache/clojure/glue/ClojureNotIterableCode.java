package mustache.clojure.glue;

import clojure.lang.IPersistentMap;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.TemplateContext;
import com.github.mustachejava.codes.NotIterableCode;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author bill
 */
public class ClojureNotIterableCode extends NotIterableCode {

    public ClojureNotIterableCode(TemplateContext templateContext, DefaultMustacheFactory df, Mustache mustache, String variable) {
        super(templateContext, df, mustache, variable);
    }

    @Override
    protected Writer handle(Writer writer, Object resolved, List<Object> scopes) {
        if (resolved instanceof Iterable || resolved == null || resolved.getClass() == Boolean.class) {
            writer = execute(writer, resolved, scopes);
        } else {
            writer = handleCallable(writer, (Callable) resolved, scopes);
        }
        return writer;
    }

}
