package com.ro;

import com.ro.example1.model.Item;
import com.ro.example2.Item2;
import com.ro.repository.Item1Repository;
import com.ro.repository.Item2Repository;
import com.ro.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataJpaApplication implements CommandLineRunner {

	@Autowired
	private Item1Repository item1Repository;

	@Autowired
	private Item2Repository item2Repository;

	public static void main(String[] args) {
		/*
			SpringApplication.run will load the standalone Spring application from the
			main method. It will create an appropriate ApplicationContext instance and load beans.
		 */
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Item i1 = new Item();
		Item i2 = new Item();
		Item i3 = new Item();

		item1Repository.save(i1);
		item1Repository.save(i2);
		item1Repository.save(i3);

		Item2 item2_1 = new Item2();
		item2_1.setName("Some Item2_1");
		item2_1.setAuctionEnd(Helper.tomorrow());

		Item2 item2_2 = new Item2();
		item2_2.setName("Some Item2_2");
		item2_2.setAuctionEnd(Helper.tomorrow());

		Item2 item2_3 = new Item2();
		item2_3.setName("Some Item2_2");
		item2_3.setAuctionEnd(Helper.tomorrow());

		item2Repository.save(item2_1);
		item2Repository.save(item2_2);
		item2Repository.save(item2_3);
	}
}
