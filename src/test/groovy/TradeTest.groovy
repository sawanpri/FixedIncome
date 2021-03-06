import com.jeff.service.PubSubHandler
import com.jeff.service.Publisher
import com.jeff.service.Subscriber
import com.jeff.domain.Topic
import spock.lang.Specification

class TradeTest extends Specification{

    def "Publish Message"(){
        println("Started test : Publish Message()")

        given:
        def path = ["src/main/resources/MockTradeData/trade1.json"
              ,"src/main/resources/MockTradeData/trade2.json",
               "src/main/resources/MockTradeData/trade3.json"]
            Publisher publisher = new Publisher(Topic.TRADE);
        when:
            Application.publishMessage(path, publisher)
        then:
            PubSubHandler.subscribers.isEmpty()
            println("Completed test : Publish Message()")

    }

    def "Register Subscribers" (){
        println("Started test : Register Subscribers()")

        given:
            Application.registerSubscribers()
        when:
            Hashtable<Topic, List<Subscriber>> map =  PubSubHandler.getSubscribers()
        then:
            map != null

        println("Completed test : Register Subscribers()")

    }

    def cleanup() {
        println('Cleaning up after a test!')
        PubSubHandler.subscribers = new Hashtable<Topic, List<Subscriber>>();
    }










}
