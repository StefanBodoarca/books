package com.ro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MetamodelTest {

    @Autowired
    EntityManager entityManager;

    @Test
    void testMetaModel() {
        Metamodel metamodel = entityManager.getMetamodel();
        Set<ManagedType<?>> managedTypes = metamodel.getManagedTypes();
        ManagedType<?> itemType = managedTypes.iterator().next();

        assertAll(() -> assertEquals(1, managedTypes.size()),
                () -> assertEquals(
                        Type.PersistenceType.ENTITY,
                        itemType.getPersistenceType()));

        SingularAttribute<?, ?> idAttribute = itemType.getSingularAttribute("id");

        assertFalse(idAttribute.isOptional());

        SingularAttribute<?, ?> nameAttribute = itemType.getSingularAttribute("name");

        assertAll(() -> assertEquals(String.class, nameAttribute.getJavaType()),
                () -> assertEquals(
                        Attribute.PersistentAttributeType.BASIC,
                        nameAttribute.getPersistentAttributeType()
                ));

        SingularAttribute<?, ?> auctionEndAttribute = itemType.getSingularAttribute("auctionEnd");

        assertAll(() -> assertEquals(Timestamp.class, auctionEndAttribute.getJavaType()),
                () -> assertFalse(auctionEndAttribute.isCollection()),
                () -> assertFalse(auctionEndAttribute.isAssociation())
        );
    }

}
