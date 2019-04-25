import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jim lin
 * @date 2019/4/24.
 */
public class Test {

    public static void main(String[] args) throws Exception{
        String json = "{\"limitApp\":\"default\",\"resource\":\"hello232\",\"grade\":1,\"count\":1.0,\"strategy\":0,\"refResource\":null,\"controlBehavior\":0,\"warmUpPeriodSec\":10,\"maxQueueingTimeMs\":500,\"clusterMode\":false}";
        ObjectMapper objectMapper = new ObjectMapper();
        FlowRule rule = objectMapper.readValue(json, FlowRule.class);
        System.out.println(rule);

        String json1 = "{\n" +
                "      \"resource\": \"/hello4\",\n" +
                "      \"limitApp\": \"default\",\n" +
                "      \"count\": 300.0,\n" +
                "      \"timeWindow\": 2,\n" +
                "      \"grade\": 0\n" +
                "    }";
        DegradeRule rule1 = objectMapper.readValue(json1, DegradeRule.class);
        System.out.println(rule1);

    }
}
