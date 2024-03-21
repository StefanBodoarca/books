import com.ro.chapter2.Message;
import com.ro.chapter2.configuration.SpringDataConfiguration;
import com.ro.chapter2.repositories.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Extend the test using SpringExtension. This extension is used to integrate the Spring test context with the JUnit 5 Jupiter test.
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringDataConfiguration.class})
public class HelloWorldSpringDataJPATest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void storeLoadMessage() {
        Message message = new Message();
        message.setText("Hello World from Spring Data JPA!");

        //the created proxy repository implemented the methods for interacting with the database: save, findAll, etc.
        messageRepository.save(message);
        List<Message> messages = (List<Message>)messageRepository.findAll();

        assertAll(
                () -> assertEquals(1, messages.size()),
                () -> assertEquals("Hello World from Spring Data JPA!",
                        messages.get(0).getText())
        );
    }
}
