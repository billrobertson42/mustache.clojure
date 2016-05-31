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

    private final String[] fieldName;
    private final Keyword[] fieldKeyword;
    private final Keyword[] dashConvertedfieldKeyword;
    private final int finalIndex;

    public PersistentMapWrapper(int scopeIndex, Wrapper[] wrappers, Guard[] guards, ObjectHandler handler, String fieldName) {
        super(guards);
        this.scopeIndex = scopeIndex;
        this.wrappers = wrappers;
        this.handler = handler;

        if (fieldName.startsWith(".") || fieldName.endsWith(".")) {
            throw new IllegalArgumentException("Field name can not start with or end with a dot");
        }

        this.fieldName = fieldName.split("\\.");
        fieldKeyword = new Keyword[this.fieldName.length];
        for (int c = 0; c < fieldKeyword.length; ++c) {
            fieldKeyword[c] = RT.keyword(null, this.fieldName[c]);
        }

        dashConvertedfieldKeyword = new Keyword[this.fieldName.length];
        for (int c = 0; c < dashConvertedfieldKeyword.length; ++c) {
            dashConvertedfieldKeyword[c] = this.fieldName[c].indexOf('_') >= 0
                    ? RT.keyword(null, this.fieldName[c].replace('_', '-')) : null;
        }
        finalIndex = this.fieldName.length - 1;
    }

    protected Object unwrap(List<Object> scopes) {
        if (wrappers == null || wrappers.length == 0) {
            return scopes.get(scopeIndex);
        } else {
            return ReflectionObjectHandler.unwrap(handler, scopeIndex, wrappers, scopes);
        }
    }

    private Object get(IPersistentMap scope, int index) {
        Object temp = scope.valAt(fieldKeyword[index]);
        if (temp == null) {
            if (dashConvertedfieldKeyword[index] != null) {
                temp = scope.valAt(dashConvertedfieldKeyword[index]);
            }
            if (temp == null) {
                temp = scope.valAt(fieldName[index]);
            }            
        }
        return temp;
    }

    @Override
    public Object call(List<Object> scopes) throws GuardException {
        guardCall(scopes);

        IPersistentMap scope = (IPersistentMap) handler.coerce(unwrap(scopes));
        if (scope == null) {
            return null;
        }

        for (int c = 0; c < finalIndex; ++c) {
            scope = (IPersistentMap) get(scope, c);
            if(scope == null) {
                return null;
            }
        }

        return get(scope, finalIndex);
    }
}
