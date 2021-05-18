package main.java.entity;

public enum PackageStatus {
    REGISTERED("Zarejestrowana"),
    RECIVED("Odebrana Od Klienta"),
    TRANSPORTING("W Transporcie"),
    IN_SORTING_DEPARTMENT("W Lokalnej Sortowni"),
    IN_MAIN_SORTING_DEPARTMENT("W Głównej Sortowni"),
    TO_DELIVERY("Przekazana Do Doręczenia"),
    DELIVERED("Dostarczona"),
    ABSENCE_OF_THE_RECIPENT("Nieobecność Odbiorcy"),
    SECOND_TRY_TO_DELIVERY("Ponowna Próba Doręczenia"),
    WAITING_IN_BRANCH("Do Odebrania W Oddziale"),
    RETURN_TO_THE_SENDER("Zwrot Do Nadawcy"),
    RETURNED_TO_THE_SENDER("Zwrócona Do Nadawcy");

    private String displayName;

    PackageStatus(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() { return displayName; }

    // Optionally and/or additionally, toString.
    @Override public String toString() { return displayName; }
}
