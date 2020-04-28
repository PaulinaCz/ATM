package atm.command;

public class WrongEntryException extends Exception {

    public WrongEntryException(){
        System.out.println("Wrong entry, please try again");
    }

}
