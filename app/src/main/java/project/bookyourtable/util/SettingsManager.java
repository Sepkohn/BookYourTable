package project.bookyourtable.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

public class SettingsManager {


    /**
     * Set the system's language
     * @param activity = actualActivity
     * @param local = language's code (ex : "fr" or "es")
     */
    public static void setLocale(Activity activity, String local){
        Locale locale = new Locale(local);

        Locale.setDefault(locale);
        Resources res = activity.getResources();

        Configuration config = res.getConfiguration();
        config.setLocale(locale);
        res.updateConfiguration(config,res.getDisplayMetrics());
        activity.getApplicationContext().createConfigurationContext(config);

    }

    /**
     * Set the dark or light mode depending on the actual state
     * @param activity = current activity
     */
    public static void setDarkMode(Activity activity) {

        Resources res = activity.getResources();

        Configuration config = res.getConfiguration();

        int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }

    }
}
