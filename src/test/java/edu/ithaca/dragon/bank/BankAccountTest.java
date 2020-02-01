package edu.ithaca.dragon.bank;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() throws InsufficientFundsException {
        //classes - fresh account, after withdrawal, after unsuccessful withdrawal
        BankAccount bankAccount = new BankAccount("a@b.com", 1000);

        //fresh account
        assertEquals(1000, bankAccount.getBalance());               //equivalence class starting balance and not border cas
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        //classes - sufficient funds, insufficient funds, negative funds
        BankAccount bankAccount = new BankAccount("a@b.com", 1000);
        //sufficient funds
        bankAccount.withdraw(100);
        assertEquals(900, bankAccount.getBalance());            //equivalence class of less than and not border case
        bankAccount.withdraw(500);
        assertEquals(400, bankAccount.getBalance());            //equivalence class of more than and not border case
        bankAccount.withdraw(400);
        assertEquals(0, bankAccount.getBalance());              //equivalence class of zero and border case
        //insufficient funds
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(max));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(1));
        //negative numbers
        BankAccount negative = new BankAccount("a@b.com", 1000);
        negative.withdraw(-1);
        assertEquals(1000, negative.getBalance());          //equivalence class of negative balance and border case
        negative.withdraw(-500);
        assertEquals(1000, negative.getBalance());          //equivalence class of negative balance and not border case
        negative.withdraw(min);
        assertEquals(1000, negative.getBalance());          //equivalence class of negative balance and border case

        BankAccount decNum = new BankAccount("a@b.com", 1000);
        assertThrows(IllegalArgumentException.class, ()->decNum.withdraw(3.898));       //equivalence class of more than 2 decimals places & not border case
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
    void isAmountValidTest(){
       int min = Integer.MIN_VALUE;
       int max = Integer.MAX_VALUE;
       //acceptable numbers
        assertTrue(BankAccount.isAmountValid(300));         //equivalence class of positive amount and not border case
        assertTrue(BankAccount.isAmountValid(max));         //equivalence class of positive amount and border case
        assertTrue(BankAccount.isAmountValid(1));         //equivalence class of positive amount and border case
        assertTrue(BankAccount.isAmountValid(0));         //equivalence class of zero amount and border case
       //negative numbers
        assertFalse(BankAccount.isAmountValid(-300));         //equivalence class of negative amount and not border case
        assertFalse(BankAccount.isAmountValid(min));         //equivalence class of negative amount and border case
        assertFalse(BankAccount.isAmountValid(-1));         //equivalence class of negative amount and border case
        //More than two decimal places number
        assertFalse(BankAccount.isAmountValid(300.129));         //equivalence class of amount has more than 2 decimal places and not border case
        assertFalse(BankAccount.isAmountValid(-300.129));         //equivalence class of negative amount, amount has more than 2 decimal places, & is not border case
   }

   @Test
    void transferTest() throws InsufficientFundsException{

       BankAccount bankAccount = new BankAccount("a@b.com", 1000);
       BankAccount bankAccount2 = new BankAccount("c@d.com", 500);

       BankAccount.transfer(bankAccount, bankAccount2, 500);            //equivalence class of positive amount and not border case
       assertEquals(1000, bankAccount2.getBalance());
       assertEquals(500, bankAccount.getBalance());

       BankAccount.transfer(bankAccount, bankAccount2, 0);              //equivalence class of zero amount and border case
       assertEquals(1000, bankAccount2.getBalance());
       assertEquals(500, bankAccount.getBalance());

       BankAccount.transfer(bankAccount, bankAccount2, 1);              //equivalence class of positive amount and border case
       assertEquals(1001, bankAccount2.getBalance());
       assertEquals(499, bankAccount.getBalance());

       assertThrows(IllegalArgumentException.class, ()->BankAccount.transfer(bankAccount, bankAccount2, -1));           //equivalence class of negative amount and border case
       assertThrows(IllegalArgumentException.class, ()->BankAccount.transfer(bankAccount, bankAccount2, -500));         //equivalence class of negative amount and not border case
       assertThrows(IllegalArgumentException.class, ()->BankAccount.transfer(bankAccount, bankAccount2, 938.845));      //equivalence class of amount has more than 2 decimal places
       assertThrows(InsufficientFundsException.class, ()->BankAccount.transfer(bankAccount, bankAccount2, 938.84));     //equivalence class of amount has more than 2 decimal places
   }


    @Test
    void depositTest(){

        BankAccount bankAccount = new BankAccount("a@b.com", 1000);

        bankAccount.deposit(500);
        assertEquals(1500, bankAccount.getBalance());       //equivalence class of positive amount and not border case
        bankAccount.deposit(0);
        assertEquals(1500, bankAccount.getBalance());       //equivalence class of zero amount and border case
        bankAccount.deposit(1);
        assertEquals(1501, bankAccount.getBalance());       //equivalence class of positive amount and border case

        int min = Integer.MIN_VALUE;

        //Negative numbers
        assertThrows(IllegalArgumentException.class, ()->bankAccount.deposit(-1));      //equivalence class of Negative amount and border case
        assertThrows(IllegalArgumentException.class, ()->bankAccount.deposit(-500));    //equivalence class of Negative amount and not border case
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(min));           //equivalence class of Negative amount and border case
        //Doubles with more than 2 decimal places
        assertThrows(IllegalArgumentException.class, ()->bankAccount.deposit(56.844));  //equivalence class of amount has more than 2 decimal places and not border case
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));        //equivalence class of not valid email and balace
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b", 945.328));        //equivalence class of not valid starting balace
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 945.328));        //equivalence class of not valid starting balace

    }
}