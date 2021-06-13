import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeff.domain.*;
import io.reactivex.Observable;
import io.reactivex.Observer;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

public class Application {




    public static void main(String[] args) {
        String[] paths ={"src/main/resources/MockTradeData/trade1.json",
                        "src/main/resources/MockTradeData/trade2.json",
                        "src/main/resources/MockTradeData/trade3.json"};


        try {

            Publisher publisher = new Publisher(Topic.TRADE);
            Subscriber tradeSubscriber1 = new Subscriber(Topic.TRADE);
            Subscriber tradeSubscriber2 = new Subscriber(Topic.TRADE);
            Subscriber tradeSubscriber3 = new Subscriber(Topic.TRADE);

            Stream.of(paths).forEach(path->{
                List<Trade> tradeData = readJSON(path);

                if(tradeData!=null) {
                    publisher.publish(tradeData);
                }
            });

            tradeSubscriber1.getLargestTradeBySize();
            tradeSubscriber1.getAveragePriceForSymbol();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static List<Trade> readJSON(String path) {
        try{
            return new ObjectMapper().readValue(Paths.get(path).toAbsolutePath().toFile(), new TypeReference<List<Trade>>(){});
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
