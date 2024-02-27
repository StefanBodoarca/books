package com.ro.services;

import com.ro.exceptions.AccountNotFoundException;
import com.ro.model.Account;
import com.ro.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTestExtendWithMockito {

    //assumptions - given
    @Mock //When you use the @Mock annotation, the framework creates and injects a mock object in the annotated attribute.
    private AccountRepository accountRepository;

    @InjectMocks //With @InjectMocks annotation, you create the object to test and instruct the framework to inject all the mocks (created with @Mock) in its parameters.
    private TransferService transferService;

    @Test
    @DisplayName("Test the amount is transferred from one account to another if no exception occurs")
    public void moneyTransferHappyFlow() {

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

    @Test
    public void moneyTransferDestinationAccountNotFoundFlow() {
        long idSender = 1L;
        Account sender = new Account();
        sender.setId(idSender);
        sender.setAmount(new BigDecimal(1000));

        long idReceiver = 2L;

        when(accountRepository.findById(sender.getId())).thenReturn(Optional.of(sender));

        //we control the mock AccountRepository to return an empty Optional when the findById() method is called for the destination account
        when(accountRepository.findById(idReceiver)).thenReturn(Optional.empty());

        //we assert that the method throws an AccountNotFoundException in the given scenario
        assertThrows(AccountNotFoundException.class, () -> transferService.transferMoney(idSender, idReceiver, new BigDecimal(100)));

        //we use the verify() method with the never() conditional to assert that the changeAmount() method hasn't been called
        verify(accountRepository, never()).changeAmount(anyLong(), any());
    }
}