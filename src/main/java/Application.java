import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeff.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        try {
            List<String> paths = Arrays.asList("src/main/resources/MockTradeData/trade1.json"
//                    ,
//                            "src/main/resources/MockTradeData/trade2.json",
//                            "src/main/resources/MockTradeData/trade3.json"
            );

            Publisher publisher = new Publisher(Topic.TRADE);
            registerSubscribers();
            publishMessage(paths, publisher);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void publishMessage(List<String> paths, Publisher publisher) {

        paths.forEach(path->{
            try {
                List<Trade> tradeData = readJSON(path);
                logger.info("JSON Consumed is "+path);
                if(tradeData!=null) {
                    publisher.publish(tradeData);
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                    e.printStackTrace();
            }
        });
    }

    static List<Subscriber> registerSubscribers() {
        Subscriber tradeSubscriber1 = new Subscriber(Topic.TRADE, "tradeSubscriber1");
        Subscriber tradeSubscriber2 = new Subscriber(Topic.TRADE, "tradeSubscriber2");
        Subscriber tradeSubscriber3 = new Subscriber(Topic.TRADE, "tradeSubscriber3");

        return Arrays.asList(tradeSubscriber1, tradeSubscriber2, tradeSubscriber3);
    }

    static List<Trade> readJSON(String path) {
        try{
            return new ObjectMapper().readValue(Paths.get(path).toAbsolutePath().toFile(), new TypeReference<List<Trade>>(){});
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
