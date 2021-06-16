import com.jeff.domain.Topic;
import com.jeff.domain.Trade
import com.jeff.service.PubSubHandler;
import com.jeff.service.Publisher;
import com.jeff.service.Subscriber;
import spock.lang.Specification;

import java.util.List;

class TradeColumnMatchTest extends Specification {

    def "get trades by group for any symbol"(){
        println("Started test : get trades by group for any symbol()")

        given:
            Publisher publisher = new Publisher(Topic.TRADE)
            List<Subscriber> subList = Application.registerSubscribers()

        when:
            def path = ["src/main/resources/MockTradeData/trade3.json"]
            Application.publishMessage(path, publisher)
            Subscriber sub = subList.stream()
                    .filter(a -> a.name.equals("tradeSubscriber1"))
                    .findAny().get()
            List<Trade> trade = sub.getTradesByGroup(sub.tradeListFinal, "size", "200").get("ABC3")
        then:
            trade.size() == 2
            trade.forEach(t -> System.out.println(t.toString()))

        when:
            def path1 = ["src/main/resources/MockTradeData/trade1.json"]
            Application.publishMessage(path1, publisher)
            Subscriber sub1 = subList.stream()
                    .filter(a -> a.name.equals("tradeSubscriber1")).findAny().get()
            List<Trade> trade1 = sub.getTradesByGroup(sub1.tradeListFinal, "size", "200").get("ABC3")
        then:
            trade1.size() == 3
            trade1.forEach(t -> System.out.println(t.toString()))
            println("Completed test : get trades by group for any symbol()")

    }


    def "get trades by group for non existing symbol"(){
        println("Started test : get trades by group for non existing symbol()")

        given:
            Publisher publisher = new Publisher(Topic.TRADE)
            List<Subscriber> subList = Application.registerSubscribers()

        when:
            def path = ["src/main/resources/MockTradeData/trade3.json"]
            Application.publishMessage(path, publisher)
            Subscriber sub = subList.stream()
                    .filter(a -> a.name.equals("tradeSubscriber1"))
                    .findAny().get()
            List<Trade> trade = sub.getTradesByGroup(sub.tradeListFinal, "size", "200")
                    .get("GHT4")
        then:
            trade == null
            println("Completed test : get trades by group for non existing symbol()")

    }

    def cleanup() {
        println('Cleaning up after a test!')
        PubSubHandler.subscribers = new Hashtable<Topic, List<Subscriber>>();
    }


}
