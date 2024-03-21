import com.ro.chapter2.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class HelloWorldJPATest {

    @Test
    public void storeLoadMessage() {
        //the factory is thread-safe, and all code in the application that accesses the database should share it.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch02");

        try {
            //Begin a new session with the database by creating an EntityManager. This is the context for all persistence operations.
            EntityManager em = emf.createEntityManager();

            //Get access to the standard transaction API, and begin a transaction on this thread of execution.
            em.getTransaction().begin();

            Message message = new Message();
            message.setText("Hello World");

            /*
                Enlist the transient instance with the persistence context; we make it persistent.
                Hibernate now knows that we wish to store that data, but it doesn't necessarily call
                the database immediately.
             */
            em.persist(message);

            /*
                Commit the transaction. Hibernate automatically checks the persistence context
                and executes the necessary SQL INSERT statement.
             */
            em.getTransaction().commit();
            //INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World!')

            /*
                Every interaction with the database should occur within transaction boundaries,
                even if we’re only reading data, so we start a new transaction. Any potential failure
                appearing from now on will not affect the previously committed transaction.
             */
            em.getTransaction().begin();

            /*
                - The select query is JPQL.
                - The Message in the query string doesn’t refer to the database table name but to the persistent class name.
                   For this reason, the Message class name in the query is case-sensitive.
                   If we map the class to a different table, the query will still work.
             */
            List<Message> messages = em.createQuery("select m from Message m", Message.class).getResultList();
            //SELECT * from MESSAGE

            /*
                We can change the value of a property. Hibernate detects this automatically
                because the loaded Message is still attached to the persistence context it was
                loaded in.
                This is the automatic dirty-checking feature of JPA in action. It saves us the effort of explicitly asking the persistence manager
                to update the database when we modify the state of an instance inside a transaction.
             */
            messages.get(messages.size() -  1).setText("Hello World from JPA!");

            /*
                On commit, Hibernate checks the persistence context for dirty state, and it executes
                the SQL UPDATE automatically to synchronize in-memory objects with the
                database state.
             */
            em.getTransaction().commit();
            //UPDATE MESSAGE set TEXT = 'Hello World from JPA!' where ID = 1

            Assertions.assertAll (
                    () -> Assertions.assertEquals(1, messages.size()),
                    () -> Assertions.assertEquals("Hello World from JPA!", messages.get(0).getText())
            );

            em.clear();
        } finally {
            emf.close();
        }
    }
}
