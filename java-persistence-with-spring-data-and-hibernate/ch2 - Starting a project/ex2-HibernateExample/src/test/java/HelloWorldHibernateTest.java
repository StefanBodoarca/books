import com.ro.chapter2.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class HelloWorldHibernateTest {

    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();

        //The execution of the configure method will load the content of the default hibernate.cfg.xml file.
        configuration.configure().addAnnotatedClass(Message.class);

        /*
            A ServiceRegistry hosts and manages
            services that need access to the SessionFactory. Services are classes that provide
            pluggable implementations of different types of functionality to Hibernate.
         */
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Test
    public void storeLoadMessage() {
        try (SessionFactory sessionFactory = createSessionFactory();
            Session session = sessionFactory.openSession()) {

            //Get access to the standard transaction API and begin a transaction on this thread of execution.
            session.beginTransaction();

            Message message = new Message();
            message.setText("Hello World from Hibernate!");

            /*
                Enlist the transient instance with the persistence context; we make it persistent.
                Hibernate now knows that we wish to store that data, but it doesn't necessarily call
                the database immediately. The native Hibernate API is pretty similar to the standard
                JPA, and most methods have the same name.
             */
            session.persist(message);

            session.getTransaction().commit();
            // INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World from Hibernate!')

            session.beginTransaction();

            /*
                A CriteriaBuilder is used to construct criteria queries, compound selections, expressions, predicates, and orderings.
                A CriteriaQuery defines functionality that is specific to top-level queries.
                CriteriaBuilder and CriteriaQuery belong to the Criteria API, which allows us to build a query programmatically.
             */
            CriteriaQuery<Message> criteriaQuery = session.getCriteriaBuilder().createQuery(Message.class);

            //Create and add a query root corresponding to the given Message entity.
            criteriaQuery.from(Message.class);

            List<Message> messages = session.createQuery(criteriaQuery).getResultList();
            //SELECT * from MESSAGE

            session.getTransaction().commit();

            Assertions.assertAll (
                    () -> Assertions.assertEquals(1, messages.size()),
                    () -> Assertions.assertEquals("Hello World from Hibernate!", messages.get(0).getText())
            );
        }
    }
}
