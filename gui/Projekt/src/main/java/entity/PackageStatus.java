package main.java.entity;

public enum PackageStatus {
    REGISTERED("Zarejestrowana"),
    RECIVED("Odebrana od klienta"),
    TRANSPORTING("W transporcie"),
    TO_DELIVERY("Przekazana do doręczenia"),
    DELIVERED("Dostarczona"),
    ABSENCE_OF_THE_RECIPENT("Nieobecność odbiorcy"),
    SECOND_TRY_TO_DELIVERY("Ponowna próba doręczenia"),
    WAITING_IN_BRANCH("Do odebrania w oddziale"),
    RETURN_TO_THE_SENDER("Zwrot do nadawcy"),
    RETURNED_TO_THE_SENDER("Zwrócona do nadawcy");

    private String displayName;

    PackageStatus(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() { return displayName; }

    // Optionally and/or additionally, toString.
    @Override public String toString() { return displayName; }
}
