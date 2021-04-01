package com.folksdevbank.repository;

import com.folksdevbank.model.Account;
import com.folksdevbank.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {

    //select * from account where balance > $(balance)
    List<Account> findAllByBalanceGreaterThan(Double balance);

    //select * from account where currency=$(currency) and balance < 100
    List<Account> findAllByCurrencyIsAndAndBalanceLessThan(Currency currency, Double balance);







}
