package cbse.test.message;

public class EmailService implements MessageService {
    private final String to;

    public EmailService(String to) {
        this.to = to;
    }

    @Override
    public void sendMessage(String msg, String from) {
        System.out.println("----------------\n"
                + "E-mail from " + from + " to " + to + ": \n"
                + msg + "\n----------------\n");
    }
}
