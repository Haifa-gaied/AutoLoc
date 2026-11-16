
package tn.esprit.tpautoloc.service.factory;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.service.observer.EmailNotificationObserver;
import tn.esprit.tpautoloc.service.observer.SmsNotificationObserver;
import tn.esprit.tpautoloc.service.observer.IReservationObserver;

import java.util.Locale;

@Component
public class NotificationFactory {

    private final EmailNotificationObserver emailNotificationObserver;
    private final SmsNotificationObserver smsNotificationObserver;

    public NotificationFactory(
            EmailNotificationObserver emailNotificationObserver,
            SmsNotificationObserver smsNotificationObserver) {

        this.emailNotificationObserver = emailNotificationObserver;
        this.smsNotificationObserver = smsNotificationObserver;
    }

    public IReservationObserver creerObservateur(String canal) {

        if (canal == null || canal.isBlank()) {
            throw new IllegalArgumentException(
                    "Le canal de notification est obligatoire"
            );
        }

        return switch (canal.trim().toUpperCase(Locale.ROOT)) {
            case "EMAIL" -> emailNotificationObserver;
            case "SMS" -> smsNotificationObserver;
            default -> throw new IllegalArgumentException(
                    "Canal de notification inconnu : " + canal
            );
        };
    }
}
