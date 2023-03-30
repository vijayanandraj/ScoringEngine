# ScoringEngine
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonConversionExample {
public static void main(String[] args) throws Exception {
String firstJson = "{\"a\":\"\",\"b\":\"\",\"c\":\"\",\"d\":\"\",\"e\":\"\",\"f\":\"\",\"g\":\"\",\"h\":\"\"}";
String secondJson = "{\"c\":\"\",\"d\":\"\"}";

        ObjectMapper mapper = new ObjectMapper();

        // Convert first JSON string to ObjectNode
        ObjectNode firstObject = mapper.readValue(firstJson, ObjectNode.class);

        // Remove all properties from firstObject except for those in secondJson
        ObjectNode secondObject = mapper.readValue(secondJson, ObjectNode.class);
        firstObject.retainAll(secondObject.fieldNames());

        // Convert secondObject to JSON string
        String outputJson = mapper.writeValueAsString(secondObject);
        System.out.println(outputJson);
    }
}
