import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeff.domain.Publisher;
import com.jeff.domain.Subscriber;
import com.jeff.domain.Topic;
import com.jeff.domain.Trade;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        String[] paths ={"src/main/resources/MockTradeData/trade1.json",
                "src/main/resources/MockTradeData/trade2.json",
                "src/main/resources/MockTradeData/trade3.json"};

        Observable<List<Trade>> publisher = new Publisher().getPublisher(paths);

        try {
            new Subscriber(publisher,"instance 1");
            new Subscriber(publisher,"instance 2");
            new Subscriber(publisher,"instance 3");

            publisher.blockingLast();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
