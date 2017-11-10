package hello;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;
    private final ConfigurableApplicationContext context;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate,
            ConfigurableApplicationContext context) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
        this.context = context;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("CONFIRM::"+rabbitTemplate.isConfirmListener());
        System.out.println("RETURN::"+rabbitTemplate.isReturnListener());
        System.out.println("Sending message...");
        Thread.sleep(10000);
        try {
            rabbitTemplate.convertAndSend(Application.queueName, "Hello from RabbitMQ!");
            //receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            ex.printStackTrace();
            Thread.sleep(10000);
        }
        Thread.sleep(100000);

        context.close();
    }

}
