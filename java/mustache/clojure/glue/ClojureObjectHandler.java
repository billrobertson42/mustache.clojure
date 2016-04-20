package mustache.clojure.glue;

import clojure.lang.IPersistentMap;
import com.github.mustachejava.Iteration;
import com.github.mustachejava.reflect.Guard;
import com.github.mustachejava.reflect.ReflectionObjectHandler;
import com.github.mustachejava.util.Wrapper;
import java.io.Writer;
import java.util.List;

/**
 *
 * @author bill
 */
public class ClojureObjectHandler extends ReflectionObjectHandler {

    @Override
    public Writer iterate(Iteration iteration, Writer writer, Object object, List<Object> scopes) {
        if (object == null) return writer;
        
        if(object instanceof IPersistentMap) {
            return iteration.next(writer, object, scopes);
        }
        else {
            return super.iterate(iteration, writer, object, scopes);
        }
    }
    
    @Override
    protected Wrapper findWrapper(int scopeIndex, Wrapper[] wrappers, List<Guard> guards, Object scope, String name) {
        scope = coerce(scope);
        if (scope == null) {
            return null;
        }
        
        if (scope instanceof IPersistentMap) {
            return new PersistentMapWrapper(scopeIndex, wrappers, guards.toArray(new Guard[guards.size()]), this, name);
        }
        
        return super.findWrapper(scopeIndex, wrappers, guards, scope, name);
    }

}
