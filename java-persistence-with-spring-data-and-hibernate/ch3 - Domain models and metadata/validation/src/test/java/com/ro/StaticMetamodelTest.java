package com.ro;

import com.ro.domain.Item;
import com.ro.domain.Item_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class StaticMetamodelTest {

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void init() {
        LocalDate futureDate = LocalDate.now().plusMonths(2);

        final Item i1 = new Item();
        i1.setName("Item 1");
        i1.setAuctionEnd(Date.from(futureDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        entityManager.persist(i1);

        final Item i2 = new Item();
        i2.setName("Item 2");
        i2.setAuctionEnd(Date.from(futureDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        entityManager.persist(i2);
    }


    @Test
    void testStaticMetamodel() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> fromItem = query.from(Item.class);

        Path<String> namePath = fromItem.get(Item_.name);
        query.where(cb.like(namePath, cb.parameter(String.class, "pattern")));

        List<Item> items = entityManager.createQuery(query).
                setParameter("pattern", "%Item 1%").
                getResultList();
        assertAll(() -> assertEquals(1, items.size()),
                () -> assertEquals("Item 1", items.iterator().next().getName()));
    }
}
