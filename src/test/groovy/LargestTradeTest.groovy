import com.jeff.domain.Topic
import com.jeff.domain.Trade
import com.jeff.service.PubSubHandler
import com.jeff.service.Publisher
import com.jeff.service.Subscriber
import spock.lang.Specification

class LargestTradeTest extends Specification {

    def "get largest trade by Size for given Symbol"(){
        given:
            def path = ["src/main/resources/MockTradeData/trade3.json"]
            Publisher publisher = new Publisher(Topic.TRADE);
            List<Subscriber> subList = Application.registerSubscribers();
            Application.publishMessage(path, publisher)
        when:
            Subscriber sub = subList.stream()
                    .filter(a -> a.name.equals("tradeSubscriber1")).findAny().get()
            List<Trade> trade = sub.largestTradeBySizeMap.get("ABC3")
        then:
            trade.size() == 2
            trade.forEach(t -> System.out.println(t.toString()))
    }

    def cleanup() {
        println('Cleaning up after a test!')
        PubSubHandler.subscribers = new Hashtable<Topic, List<Subscriber>>();
    }

}
