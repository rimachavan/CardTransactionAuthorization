import org.highnote.transaction.service.*;

public class MainApp {
    public static void main(String[] args) {
        CardTransactionAuthorizationService app = new CardTransactionAuthorizationService(
                new CardTransactionReader(),
                new CardTransactionWriter(),
                new CardTransactionValidator(),
                new CardTransactionAuthorizer());

        if(args == null || args.length == 0) {
            System.out.println("Please provide a filename!!");
            return;
        }
        app.startAuthorization(args[0]);
    }
}
