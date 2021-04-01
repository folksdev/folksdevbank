package com.folksdevbank;

import com.folksdevbank.model.*;
import com.folksdevbank.repository.AccountRepository;
import com.folksdevbank.repository.AddressRepository;
import com.folksdevbank.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class FolksdevbankApplication implements CommandLineRunner {

	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;
	private final AddressRepository addressRepository;

	public FolksdevbankApplication(AccountRepository accountRepository,
								   CustomerRepository customerRepository,
								   AddressRepository addressRepository) {
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
		this.addressRepository = addressRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(FolksdevbankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer c1 = Customer.builder()
				.id("1234568")
				.name("Cagri")
				.address(Arrays.asList(Address.builder().city(City.ISTANBUL).postcode("456312").addressDetails("bu bir adrestir").build()))
				.city(City.ISTANBUL)
				.dateOfBirth(1988)
				.build();


		Customer c2 = Customer.builder()
				.id("789456")
				.name("Semih")
				.city(City.MANISA)
				.address(Arrays.asList(Address.builder().city(City.MANISA).postcode("456312").addressDetails("bu bir adrestir 2").build()))
				.dateOfBirth(2000)
				.build();

		Customer c3 = Customer.builder()
				.id("456238")
				.name("untpleax")
				.city(City.IZMIR)
				 .address(Arrays.asList(Address.builder().city(City.IZMIR).postcode("456312").addressDetails("bu bir adrestir 3").build()))
				.dateOfBirth(2005)
				.build();

		customerRepository.saveAll(Arrays.asList(c1,c2,c3));

		//Address addresses = Address.builder().customer(c1).city(City.ISTANBUL).postcode("456312").addressDetails("bu bir adrestir").build();
		//addressRepository.save(addresses);

		Account a1 = Account.builder()
				.id("100")
				.customerId("1234568")
				.city(City.ISTANBUL)
				.balance(1320.0)
				.currency(Currency.TRY)
				.build();
		Account a2 = Account.builder()
				.id("101")
				.customerId("789456")
				.city(City.ISTANBUL)
				.balance(7898.0)
				.currency(Currency.TRY)
				.build();
		Account a3 = Account.builder()
				.id("102")
				.customerId("456238")
				.city(City.ISTANBUL)
				.balance(120000.0)
				.currency(Currency.TRY)
				.build();

		accountRepository.saveAll(Arrays.asList(a1,a2,a3));
	}



}

