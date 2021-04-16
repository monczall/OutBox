package main.java.preferences;

import java.util.prefs.Preferences;

public class Preference {

    //Function allows to save preference in Windows registry, first argument is name of preference and second argument is a value of that preference.
    public void addPreference(String name, String value) {
        Preferences preference = Preferences.userNodeForPackage(Preference.class);
        preference.put(name, value);
    }

    //Function allows to read preference from Windows registry, it take one argument which is name of preference.
    public static String readPreference(String name){
        Preferences preferences = Preferences.userNodeForPackage(Preference.class);
        return preferences.get(name,"Name of preference couldn't be found");
    }
}
