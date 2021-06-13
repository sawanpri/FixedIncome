import com.jeff.domain.PubSubHandler
import com.jeff.domain.Publisher
import com.jeff.domain.Subscriber
import com.jeff.domain.Topic
import spock.lang.Specification

class TradeTest extends Specification{

    Publisher publisher = new Publisher(Topic.TRADE);
    def application = Mock(Application)
    def path = ["src/main/resources/MockTradeData/trade1.json","src/main/resources/MockTradeData/trade2.json",
                "src/main/resources/MockTradeData/trade3.json"]




    def "Register Subscribers" (){
        when: Application.registerSubscribers()
        then:
        Hashtable<Topic, List<Subscriber>> map =  PubSubHandler.subscribers
        map.values().forEach(a -> a.forEach(b ->
                System.out.println(b.name)))
        map != null
    }

    def "Publish Message"(){
        when:
        Application.publishMessage(path, publisher)
        then:
            PubSubHandler.subscribers == null

    }

    def "Register Subscriber and Publish message"(){
        when:
        Application.registerSubscribers();
        Application.publishMessage()
        then:
        PubSubHandler.subscribers.values().forEach()
    }



}
