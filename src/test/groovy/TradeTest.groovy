import com.jeff.domain.PubSubHandler
import com.jeff.domain.Publisher
import com.jeff.domain.Subscriber
import com.jeff.domain.Topic
import com.jeff.domain.Trade
import spock.lang.Specification

class TradeTest extends Specification{


    def path = [//"src/main/resources/MockTradeData/trade1.json"
//                ,"src/main/resources/MockTradeData/trade2.json",
               "src/main/resources/MockTradeData/trade3.json"
    ]

    def cleanup() {
        println('Cleaning up after a test!')
        PubSubHandler.subscribers = new Hashtable<Topic, List<Subscriber>>();
    }

    def "Publish Message"(){
        given:
            Publisher publisher = new Publisher(Topic.TRADE);
        when:
            Application.publishMessage(path, publisher)
        then:
            PubSubHandler.subscribers.isEmpty()
    }

    def "Register Subscribers" (){
        given:
            Application.registerSubscribers()
        when:
            Hashtable<Topic, List<Subscriber>> map =  PubSubHandler.subscribers
        then:
            map != null
    }



    def "get average price for given Symbol"(){
        given:
            Publisher publisher = new Publisher(Topic.TRADE);
            List<Subscriber> subList = Application.registerSubscribers();
            Application.publishMessage(path, publisher)
        when:
            Subscriber sub = subList.stream()
                    .filter(a -> a.name.equals("tradeSubscriber1")).findAny().get()
            def avgPrice = sub.averagePriceMap.get("ABC3")
        then:
            println(avgPrice)
            avgPrice == 199
    }


    def "get largest trade by Size for given Symbol"(){
        given:
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


    def "get trades by group for any symbol"(){
        given:
        Publisher publisher = new Publisher(Topic.TRADE);
        List<Subscriber> subList = Application.registerSubscribers();
        Application.publishMessage(path, publisher)
        when:
        Subscriber sub = subList.stream()
                .filter(a -> a.name.equals("tradeSubscriber1")).findAny().get()
        List<Trade> trade = sub.getTradesByGroup(sub.tradeListFinal, "size", "200").get("ABC3")
        then:
        trade != null
        trade.forEach(t -> System.out.println(t.toString()))
    }


}
