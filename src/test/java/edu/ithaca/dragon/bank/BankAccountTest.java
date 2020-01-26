package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //equivalence class for one @, an edge case (either one, none, or multiple)
        assertFalse( BankAccount.isEmailValid("")); // one @ class, since it has none, it is an edge
        //prefix
        assertFalse(BankAccount.isEmailValid("abc-@mail.com")); //special character in prefix class, is an edge case because it comes right before the @, and it cannot do that
        assertFalse(BankAccount.isEmailValid("abc..@mail.com")); //special character in prefix class, edge case, since two are next to each other
        assertFalse(BankAccount.isEmailValid(".abc@mail.com")); //special character in prefix class, edge case because it is in the front
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com")); //invalid character in prefix class, not an edge case, just an invalid character
        //suffix
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c"));//invalid domain class, domain must have minimum 2 letters, edge case because it has one
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com")); //invalid character in suffix class, not an edge case
        assertFalse(BankAccount.isEmailValid("abc.def@mail")); //invalid domain class, an edge because it has none
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com")); // invalid character in suffix class, edge case because it has 2 next to each other
        //more than one @
        assertFalse(BankAccount.isEmailValid("abc@def@mail.com")); //one @ class, edge case because it has 2
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}