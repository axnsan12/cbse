package cbse.test.message;

public class SmsService implements MessageService {
    private final String to;

    public SmsService(String to) {
        this.to = to;
    }

    @Override
    public void sendMessage(String msg, String from) {
        System.out.println("----------------\n"
                + "SMS from " + from + " to " + to + ": \n"
                + msg + "\n----------------\n");
    }
}
