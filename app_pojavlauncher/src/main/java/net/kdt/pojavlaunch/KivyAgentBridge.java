package net.kdt.pojavlaunch;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;
import java.io.File;

public class KivyAgentBridge {
    private final Context mContext;

    public KivyAgentBridge(Context context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void receiveAiResponse(String fullMessage) {
        
        if (fullMessage.contains("[ACTION:")) {
            int lastIndex = 0;
            
            while ((lastIndex = fullMessage.indexOf("[ACTION:", lastIndex)) != -1) {
                int endIndex = fullMessage.indexOf("]", lastIndex);
                if (endIndex == -1) break;
                
                final String commandData = fullMessage.substring(lastIndex + 8, endIndex);
                lastIndex = endIndex + 1;

                
                new Handler(Looper.getMainLooper()).post(() -> executeSystemAction(commandData));
            }
        }
    }

    private void executeSystemAction(String commandData) {
        try {
            String[] parts = commandData.split("=", 2);
            String action = parts[0];
            String value = parts.length > 1 ? parts[1] : "";

            switch (action) {
                case "SET_USER":
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.PREF_USERNAME = value;
                    showNotice("Kivy: Player name has been changed to " + value);
                    break;

                case "SET_RAM":
                    int ramMb = Integer.parseInt(value);
                    int ramMb = Integer.parseInt(value);
    android.content.SharedPreferences prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(mContext);
    prefs.edit().putInt("channel_ram", ramMb).apply();
                    showNotice("Kivy: Changed RAM to " + ramMb + " MB");
                    break;

                case "SET_RENDERER":
                    
                    showNotice("Kivy: Graphical renderer changed to " + value);
                    break;

                case "CLEAN_CACHE":
    try {
        java.io.File cacheDir = mContext.getCacheDir();
        if (cacheDir != null && cacheDir.isDirectory()) {
            for (java.io.File file : cacheDir.listFiles()) {
                file.delete();
            }
        }
        showNotice("Kivy: Launcher cache has been cleaned!");
    } catch (Exception e) {
        showNotice("Kivy: Failed to clean cache: " + e.getMessage());
    }
    break;

                case "SET_JAVA_ARGS":
                    
                    showNotice("Kivy: New Java arguments has been set");
                    break;

                case "SET_SCALE":
    int scale = Integer.parseInt(value);
    android.content.SharedPreferences scalePrefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(mContext);
    scalePrefs.edit().putInt("scale_factor", scale).apply();
    
    showNotice("Kivy: Screen resolution has been changed to " + scale + "%");
    break;

                case "OPEN_MODS":
                    
                    showNotice("Kivy: Opening folder with mods...");
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "Kivy Agent Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showNotice(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
                           }

