package cbse.test.message;

import cbse.ioc.CbseIoc;
import cbse.ioc.InjectorConfig;

import java.io.FileNotFoundException;

public class Main {
    private static InjectorConfig smsConfig() {
        return new InjectorConfig()
                .registerImpl(Watcher.class, new Object[] {"Zabbix"})
                .registerImpl(MessageService.class, SmsService.class, new Object[] {"+407-PANICA"});
    }

    private static InjectorConfig emailConfig() throws FileNotFoundException, ClassNotFoundException {
        return InjectorConfig.fromJson("watcher-di-config.json");
    }

    private static void testIoc(InjectorConfig config) {
        CbseIoc ioc = new CbseIoc(config);
        Watcher watcher = ioc.getInstance(Watcher.class);
        watcher.alert("s-a stricat");
    }

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
        testIoc(smsConfig());
        testIoc(emailConfig());
    }
}
