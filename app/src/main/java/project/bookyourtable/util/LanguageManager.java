package project.bookyourtable.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {

    /*public static void changeLanguage(Context context, String local){
        Configuration configuration = Resources.getSystem().getConfiguration();
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        switch (local){
            case("fr"):
                configuration.setLocale(Locale.FRANCE);
                break;
            default:
                configuration.setLocale(new Locale("es"));
        }
       context.createConfigurationContext(configuration);
    }*/

    public static void setLocale(Activity activity, String local){
        Locale locale = new Locale(local);

        Locale.setDefault(locale);
        Resources res = activity.getResources();

        Configuration config = res.getConfiguration();
        config.setLocale(locale);
        res.updateConfiguration(config,res.getDisplayMetrics());
        activity.getApplicationContext().createConfigurationContext(config);

    }
}
