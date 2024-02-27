package com.ro.services;

import com.ro.model.Account;
import com.ro.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    @Test
    @DisplayName("Test the amount is transferred from one account to another if no exception occurs")
    public void moneyTransferHappyFlow() {
        //assumptions - given
        AccountRepository accountRepository = mock(AccountRepository.class);

        TransferService transferService = new TransferService(accountRepository);

        long idSender = 1L;
        Account sender = new Account();
        sender.setId(idSender);
        sender.setAmount(new BigDecimal(1000));

        long idReceiver = 2L;
        Account destination = new Account();
        destination.setId(idReceiver);
        destination.setAmount(new BigDecimal(1000));

        when(accountRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
        when(accountRepository.findById(destination.getId())).thenReturn(Optional.of(destination));

        //call - when
        transferService.transferMoney(
                sender.getId(),
                destination.getId(),
                new BigDecimal(100)
        );

        //verify that the changeAmount() method in the AccountRepository was called with the expected parameters
        //validations - then
        verify(accountRepository).changeAmount(idSender, new BigDecimal(900));
        verify(accountRepository).changeAmount(idReceiver, new BigDecimal(1100));
    }

}