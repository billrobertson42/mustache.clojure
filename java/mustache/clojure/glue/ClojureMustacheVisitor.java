package mustache.clojure.glue;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.DefaultMustacheVisitor;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.TemplateContext;

/**
 *
 * @author bill
 */
public class ClojureMustacheVisitor extends DefaultMustacheVisitor  {

    public ClojureMustacheVisitor(DefaultMustacheFactory df) {
        super(df);
    }

    @Override
    public void iterable(TemplateContext templateContext, String variable, Mustache mustache) {
        list.add(new ClojureIterableCode(templateContext, df, mustache, variable));
    }

}
