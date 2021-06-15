import com.jeff.domain.Topic
import com.jeff.service.PubSubHandler
import com.jeff.service.Publisher
import com.jeff.service.Subscriber
import spock.lang.Specification

class AveragePriceTest extends Specification {

    def "get average price for given Symbol"(){
        given:
        Publisher publisher = new Publisher(Topic.TRADE);
        List<Subscriber> subList = Application.registerSubscribers();
        when:
        def path = ["src/main/resources/MockTradeData/trade3.json"]

        Application.publishMessage(path, publisher)
        Subscriber sub = subList.stream()
                .filter(a -> a.name.equals("tradeSubscriber1")).findAny().get()
        def avgPrice = sub.averagePriceMap.get("ABC3")
        then:
        println(avgPrice)
        avgPrice == 199

        when:
        def path1 = ["src/main/resources/MockTradeData/trade1.json"]
        Application.publishMessage(path1, publisher)

        Subscriber sub1 = subList.stream()
                .filter(a -> a.name.equals("tradeSubscriber1")).findAny().get()
        def avgPrice1 = sub1.averagePriceMap.get("ABC3")
        then:
        println(avgPrice1)
        avgPrice1 == 258.6667
    }

    def "get average price for non existing Symbol"(){
        given:
        Publisher publisher = new Publisher(Topic.TRADE);
        List<Subscriber> subList = Application.registerSubscribers();
        when:
        def path = ["src/main/resources/MockTradeData/trade3.json"]

        Application.publishMessage(path, publisher)
        Subscriber sub = subList.stream()
                .filter(a -> a.name.equals("tradeSubscriber1")).findAny().get()
        def avgPrice = sub.averagePriceMap.get("GHT4")
        then:
        println(avgPrice)
        avgPrice == null
    }

    def cleanup() {
        println('Cleaning up after a test!')
        PubSubHandler.subscribers = new Hashtable<Topic, List<Subscriber>>();
    }

}
