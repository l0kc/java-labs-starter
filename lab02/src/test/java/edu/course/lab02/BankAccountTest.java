package edu.course.lab02;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void testWithdraw() {
    BankAccount account = new BankAccount(1000);
    account.withdraw(300);
    assertEquals(700, account.getBalance());
}
    @Test
    void testDeposit() {
        BankAccount account = new BankAccount(1000);
        account.deposit(300);
        assertEquals(1300, account.getBalance());
    }

    @Test
    void testWithdrawError() {
    BankAccount account = new BankAccount(500);
    assertThrows(IllegalArgumentException.class, () -> {
        account.withdraw(1000);
    });

    assertEquals(500, account.getBalance());
    }
    @Test
    void testDepositError() {
        BankAccount account = new BankAccount(500);

        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-100);
        });
        assertEquals(500, account.getBalance());
    }
}
//class ProjectTask{
//    @Test
//    void main{
//        assertTrue(true);
//    }
//}