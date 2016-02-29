package mustache.clojure.glue;

import clojure.lang.IPersistentMap;
import clojure.lang.Keyword;
import clojure.lang.RT;
import com.github.mustachejava.ObjectHandler;
import com.github.mustachejava.reflect.Guard;
import com.github.mustachejava.reflect.GuardedWrapper;
import com.github.mustachejava.reflect.ReflectionObjectHandler;
import com.github.mustachejava.util.GuardException;
import com.github.mustachejava.util.Wrapper;
import java.util.List;

/**
 *
 * @author bill
 */
public class PersistentMapWrapper extends GuardedWrapper {

    protected final int scopeIndex;
    protected final Wrapper[] wrappers;
    protected final ObjectHandler handler;
    
    protected final String fieldName;
    protected final Keyword fieldKeyword;
    protected final Keyword dashConvertedfieldKeyword;

    public PersistentMapWrapper(int scopeIndex, Wrapper[] wrappers, Guard[] guards, ObjectHandler handler, String fieldName) {
        super(guards);
        this.scopeIndex = scopeIndex;
        this.wrappers = wrappers;
        this.handler = handler;
        
        this.fieldName = fieldName;
        this.fieldKeyword = RT.keyword(null, fieldName);
        this.dashConvertedfieldKeyword = fieldName.indexOf('_') >= 0 ? RT.keyword(null, fieldName.replace('_', '-')) : null;
    }

    protected Object unwrap(List<Object> scopes) {
        if (wrappers == null || wrappers.length == 0) {
            return scopes.get(scopeIndex);
        } else {
            return ReflectionObjectHandler.unwrap(handler, scopeIndex, wrappers, scopes);
        }
    }

    @Override
    public Object call(List<Object> scopes) throws GuardException {
        guardCall(scopes);
        
        IPersistentMap scope = (IPersistentMap)handler.coerce(unwrap(scopes));
        if(scope == null) {
            return null;
        }
        
        Object value = scope.valAt(fieldKeyword);
        if(value == null) {
            if(dashConvertedfieldKeyword != null) {
                value = scope.valAt(dashConvertedfieldKeyword);
            }
            
            if(value == null) {
                value = scope.valAt(fieldName);
            }
        }        
        return value;        
    }
}
