package com.example.DigitalBanking;

import com.example.DigitalBanking.entites.AccountOperation;
import com.example.DigitalBanking.entites.AppUser;
import com.example.DigitalBanking.entites.BankAccount;
import com.example.DigitalBanking.entites.Customer;
import com.example.DigitalBanking.entites.OperationType;
import com.example.DigitalBanking.entites.Role;
import com.example.DigitalBanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class DigitalBankingApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private AccountOperationRepository accountOperationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Create roles
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);

		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);

		// Create some mock users
		List<AppUser> users = List.of(
				new AppUser(null, "admin", passwordEncoder.encode("admin")),
				new AppUser(null, "user1", passwordEncoder.encode("password1")),
				new AppUser(null, "user2", passwordEncoder.encode("password2"))
		);

		// Assign roles to users
		users.forEach(user -> {
			Set<Role> roles = new HashSet<>();
			if (user.getUsername().equals("admin")) {
				roles.add(adminRole);
			}
			roles.add(userRole);
			user.setRoles(roles);
		});

		// Save users to repository
		userRepository.saveAll(users);

		// Create some mock customers
		List<Customer> customers = List.of(
				new Customer(null, "Mohamed Amine", "mohamed.amine@example.com"),
				new Customer(null, "Fatima Zahra", "fatima.zahra@example.com"),
				new Customer(null, "Yassir El Fadili", "yassir.elfadili@example.com"),
				new Customer(null, "Amina Bensalem", "amina.bensalem@example.com"),
				new Customer(null, "Hassan Mouhajir", "hassan.mouhajir@example.com"),
				new Customer(null, "Salma Kabbaj", "salma.kabbaj@example.com"),
				new Customer(null, "Omar Saidi", "omar.saidi@example.com"),
				new Customer(null, "Layla Boukili", "layla.boukili@example.com")
		);

		// Save customers to repository
		customerRepository.saveAll(customers);

		// Create some mock bank accounts
		customers.forEach(customer -> {
			AppUser user = users.get((int) (Math.random() * users.size())); // Assign a random user to the account

			BankAccount currentAccount = new BankAccount();
			currentAccount.setId(UUID.randomUUID().toString());
			currentAccount.setBalance(Math.random() * 10000);
			currentAccount.setCustomer(customer);
			currentAccount.setType("CURRENT");
			currentAccount.setCreatedAt(new Date());
			currentAccount.setCreatedBy(user);

			BankAccount savingAccount = new BankAccount();
			savingAccount.setId(UUID.randomUUID().toString());
			savingAccount.setBalance(Math.random() * 20000);
			savingAccount.setCustomer(customer);
			savingAccount.setType("SAVING");
			savingAccount.setCreatedAt(new Date());
			savingAccount.setCreatedBy(user);

			bankAccountRepository.save(currentAccount);
			bankAccountRepository.save(savingAccount);

			// Create some mock account operations for currentAccount
			for (int i = 0; i < 5; i++) {
				AccountOperation operation = new AccountOperation();
				operation.setOperationDate(new Date());
				operation.setAmount(Math.random() * 1000);
				operation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
				operation.setBankAccount(currentAccount);
				operation.setCreatedBy(user);
				accountOperationRepository.save(operation);
			}

			// Create some mock account operations for savingAccount
			for (int i = 0; i < 5; i++) {
				AccountOperation operation = new AccountOperation();
				operation.setOperationDate(new Date());
				operation.setAmount(Math.random() * 1000);
				operation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
				operation.setBankAccount(savingAccount);
				operation.setCreatedBy(user);
				accountOperationRepository.save(operation);
			}
		});
	}
}
