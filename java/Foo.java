
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import java.io.File;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Map;

public class Foo {

//    public static String transformc(String json, String templateText) throws Exception {
//        
//        RT.load("cheshire/core");
//        
//        IFn parse = Clojure.var("cheshire.core", "parse-string");
//        Object input = parse.invoke(json, true);
//        Files.write(new File("template.mustache").toPath(), templateText.getBytes("UTF-8"));
//        ClojureMustacheFactory cmf = new ClojureMustacheFactory(new File("."));
//        Mustache m = cmf.compile("template.mustache");
//        StringWriter sw = new StringWriter();
//        m.execute(sw, input);
//        return sw.toString();
//    }        
    
    public static String transform(String json, String templateText) throws Exception {
        Files.write(new File("template.mustache").toPath(), templateText.getBytes("UTF-8"));
        Mustache m = new DefaultMustacheFactory(new File(".")).compile("template.mustache");
        Map data = new ObjectMapper().readValue(json, Map.class);
        StringWriter sw = new StringWriter();
        m.execute(sw, data);
        return sw.toString();                
    }
    
    public static void main(String[] args) throws Exception { 
        String json = "{\"a\": {\"b\": {}}, \"b\": {\"c\": \"ERROR\"}}";
        String template = "{{#a}}{{b.c}}{{/a}}"; 
        
        System.out.println("Testing template: "+ template);
        System.out.println("  With data: "+json);
        String output = transform(json, template);
        System.out.println("result: '" + output + "'");
    }
}
