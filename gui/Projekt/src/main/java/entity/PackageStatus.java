package main.java.entity;

import main.java.App;
import main.java.features.Preference;

public enum PackageStatus {
    REGISTERED("Zarejestrowana", "Registered"),
    RECIVED("Odebrana Od Klienta", "Received From Client"),
    TRANSPORTING("W Transporcie", "In Transport"),
    IN_SORTING_DEPARTMENT("W Lokalnej Sortowni", "In Local Hub"),
    IN_MAIN_SORTING_DEPARTMENT("W Glownej Sortowni", "In Main Hub"),
    TO_DELIVERY("Przekazana Do Doreczenia", "Handed Over For Delivery"),
    DELIVERED("Dostarczona", "Delivered"),
    ABSENCE_OF_THE_RECIPENT("Nieobecnosc Odbiorcy", "Recipient's Absence"),
    SECOND_TRY_TO_DELIVERY("Ponowna Proba Doreczenia", "Retry Delivery"),
    WAITING_IN_BRANCH("Do Odebrania W Oddziale", "To Be Picked In Hub"),
    RETURN_TO_THE_SENDER("Zwrot Do Nadawcy", "Returning To The Sender"),
    RETURNED_TO_THE_SENDER("Zwrocona Do Nadawcy", "Returned To The Sender");

    private String displayName;
    private String engDisplayName;

    PackageStatus(String displayName, String engDisplayName) {
        this.displayName = displayName;
        this.engDisplayName = engDisplayName;
    }

    public String displayName() { return displayName; }

    public String engDisplayName() { return engDisplayName; }

    @Override
    public String toString() {
        if(Preference.readPreference("language").equals("english")){
            return engDisplayName;
        }
        else return displayName;
    }
}
