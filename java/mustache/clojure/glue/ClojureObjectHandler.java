package mustache.clojure.glue;

import clojure.lang.IPersistentMap;
import com.github.mustachejava.reflect.Guard;
import com.github.mustachejava.reflect.ReflectionObjectHandler;
import com.github.mustachejava.util.Wrapper;
import java.util.List;

/**
 *
 * @author bill
 */
public class ClojureObjectHandler extends ReflectionObjectHandler {

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
