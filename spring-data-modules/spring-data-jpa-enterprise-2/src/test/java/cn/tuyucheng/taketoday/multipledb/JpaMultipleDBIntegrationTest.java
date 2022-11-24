package cn.tuyucheng.taketoday.multipledb;

import cn.tuyucheng.taketoday.multipledb.dao.product.ProductRepository;
import cn.tuyucheng.taketoday.multipledb.dao.user.PossessionRepository;
import cn.tuyucheng.taketoday.multipledb.dao.user.UserRepository;
import cn.tuyucheng.taketoday.multipledb.model.product.Product;
import cn.tuyucheng.taketoday.multipledb.model.user.Possession;
import cn.tuyucheng.taketoday.multipledb.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MultipleDbApplication.class)
@EnableTransactionManagement
class JpaMultipleDBIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PossessionRepository possessionRepository;

	@Autowired
	private ProductRepository productRepository;

	@Test
	@Transactional("userTransactionManager")
	void whenCreatingUser_thenCreated() {
		User user = new User();
		user.setName("John");
		user.setEmail("john@test.com");
		user.setAge(20);
		Possession p = new Possession("sample");
		p = possessionRepository.save(p);
		user.setPossessionList(Collections.singletonList(p));
		user = userRepository.save(user);
		final Optional<User> result = userRepository.findById(user.getId());
		assertTrue(result.isPresent());
		System.out.println(result.get().getPossessionList());
		assertEquals(1, result.get().getPossessionList().size());
	}

	@Test
	@Transactional("userTransactionManager")
	void whenCreatingUsersWithSameEmail_thenRollback() {
		User user1 = new User();
		user1.setName("John");
		user1.setEmail("john@test.com");
		user1.setAge(20);
		user1 = userRepository.save(user1);
		assertTrue(userRepository.findById(user1.getId()).isPresent());

		User user2 = new User();
		user2.setName("Tom");
		user2.setEmail("john@test.com");
		user2.setAge(10);
		try {
			user2 = userRepository.save(user2);
			userRepository.flush();
			fail("DataIntegrityViolationException should be thrown!");
		} catch (final DataIntegrityViolationException e) {
			// Expected
		} catch (final Exception e) {
			fail("DataIntegrityViolationException should be thrown, instead got: " + e);
		}
	}

	@Test
	@Transactional("productTransactionManager")
	void whenCreatingProduct_thenCreated() {
		Product product = new Product();
		product.setName("Book");
		product.setId(2);
		product.setPrice(20);
		product = productRepository.save(product);

		assertTrue(productRepository.findById(product.getId()).isPresent());
	}
}