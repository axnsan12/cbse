package cbse.test.message;

public class Watcher {
    private final String from;
    private final MessageService service;

    public Watcher(String from, MessageService service) {
        this.from = from;
        this.service = service;
    }

    public void alert(String msg) {
        this.service.sendMessage(msg, from);
    }
}
