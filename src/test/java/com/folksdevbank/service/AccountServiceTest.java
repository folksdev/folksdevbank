package com.folksdevbank.service;

import com.folksdevbank.dto.AccountDto;
import com.folksdevbank.dto.AccountDtoConverter;
import com.folksdevbank.dto.CreateAccountRequest;
import com.folksdevbank.model.*;
import com.folksdevbank.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.kafka.core.KafkaTemplate;

public class AccountServiceTest {

    private AccountService accountService;

    private AccountRepository accountRepository;
    private CustomerService customerService;
    private AccountDtoConverter accountDtoConverter;
    private DirectExchange exchange;
    private AmqpTemplate rabbitTemplate;
    private KafkaTemplate kafkaTemplate;

    @Before
    public void setUp() throws Exception {
        accountRepository = Mockito.mock(AccountRepository.class);
        customerService = Mockito.mock(CustomerService.class);
        accountDtoConverter = Mockito.mock(AccountDtoConverter.class);
        exchange = Mockito.mock(DirectExchange.class);
        rabbitTemplate = Mockito.mock(AmqpTemplate.class);
        kafkaTemplate = Mockito.mock(KafkaTemplate.class);

        accountService = new AccountService(accountRepository,
                                            customerService,
                                            accountDtoConverter, exchange, rabbitTemplate, kafkaTemplate);
    }

    @Test
    public void whenCreateAccountCalledWithValidRequest_itShouldReturnValidAccountDto(){
        CreateAccountRequest createAccountRequest = generateCreateAccountRequest();
        Customer customer = generateCustomer();
        Account account = generateAccount(createAccountRequest);
        AccountDto accountDto = generateAccountDto();

        //Determine mock services behavior regarding test scenario
        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);

        //Call the testing method
        AccountDto result = accountService.createAccount(createAccountRequest);

        //Check results and verify the mock methods are called
        Assert.assertEquals(result, accountDto);
        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithNonExistCustomer_itShouldThrowRuntimeException(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.TRY);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder().build());

        AccountDto expectedAccountDto = AccountDto.builder().build();

        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result, expectedAccountDto);
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithWithEmptyCustomerId_itShouldThrowRuntimeException(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.TRY);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder()
                .id(" ")
                .build());

        AccountDto expectedAccountDto = AccountDto.builder().build();

        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result, expectedAccountDto);
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledAndRepositoryThrewException_itShouldThrowException() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.TRY);

        Customer customer = Customer.builder()
                .id("12345")
                .address(Address.builder().city(City.ISTANBUL).postcode("456312").addressDetails("bu bir adrestir").build())
                .city(City.ISTANBUL)
                .dateOfBirth(1998)
                .name("Muratcan")
                .build();

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();


        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenThrow(new RuntimeException("bla bla"));

        accountService.createAccount(createAccountRequest);

        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

    private CreateAccountRequest generateCreateAccountRequest(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.TRY);
        return createAccountRequest;
    }

    private Customer generateCustomer() {
        return Customer.builder()
                .id("12345")
                .address(Address.builder().city(City.ISTANBUL).postcode("456312").addressDetails("bu bir adrestir").build())
                .city(City.ISTANBUL)
                .dateOfBirth(1998)
                .name("Muratcan")
                .build();
    }

    private Account generateAccount(CreateAccountRequest accountRequest) {
        return Account.builder()
                .id(accountRequest.getId())
                .balance(accountRequest.getBalance())
                .currency(accountRequest.getCurrency())
                .customerId(accountRequest.getCustomerId())
                .city(accountRequest.getCity())
                .build();
    }

    private AccountDto generateAccountDto() {
        return AccountDto.builder()
                .id("1234")
                .customerId("12345")
                .currency(Currency.TRY)
                .balance(100.0)
                .build();
    }


}