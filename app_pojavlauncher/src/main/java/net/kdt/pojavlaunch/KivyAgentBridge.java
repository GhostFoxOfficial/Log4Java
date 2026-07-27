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
        if (fullMessage == null || !fullMessage.contains("[ACTION:")) return;

        int searchIndex = 0;
        while (true) {
            int startIndex = fullMessage.indexOf("[ACTION:", searchIndex);
            if (startIndex == -1) break;

            int endIndex = fullMessage.indexOf("]", startIndex);
            if (endIndex == -1) break;

            final String commandData = fullMessage.substring(startIndex + 8, endIndex);
            searchIndex = endIndex + 1;

            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> executeSystemAction(commandData));
        }
    }

    private void executeSystemAction(String actionData) {
        try {
            if (actionData == null || !actionData.contains("=")) return;

            String[] parts = actionData.split("=", 2);
            String action = parts[0].trim();
            String value = parts[1].trim();

            switch (action) {
                case "SET_USER":
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.DEFAULT_PREF.edit()
                        .putString("username", value)
                        .apply();
                    showNotice("Kivy: Nickname changed to " + value);
                    break;

                case "SET_RAM":
                    int ramValue = Integer.parseInt(value);
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.DEFAULT_PREF.edit()
                        .putInt("allocation", ramValue)
                        .apply();
                    showNotice("Kivy: Allocated RAM set to " + ramValue + "MB");
                    break;

                case "SET_RENDERER":
                    String rendererValue = value.toLowerCase();
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.DEFAULT_PREF.edit()
                        .putString("renderer", rendererValue)
                        .apply();
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.PREF_RENDERER = rendererValue;
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
                        showNotice("Kivy Cache Error: " + e.getMessage());
                    }
                    break;

                case "SET_JAVA_ARGS":
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.DEFAULT_PREF.edit()
                        .putString("customJavaArgs", value)
                        .apply();
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.PREF_CUSTOM_JAVA_ARGS = value;
                    showNotice("Kivy: New Java arguments has been set!");
                    break;

                case "SET_SCALE":
                    float scaleFactor = Integer.parseInt(value) / 100.0f;
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.DEFAULT_PREF.edit()
                        .putFloat("scaleFactor", scaleFactor)
                        .apply();
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.PREF_SCALE_FACTOR = scaleFactor;
                    showNotice("Kivy: Screen resolution has been changed to " + value + "%");
                    break;

                case "OPEN_MODS":
                    try {
                        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
                        java.io.File modsDir = new java.io.File(mContext.getFilesDir(), "minecraft/mods");
                        if (!modsDir.exists()) modsDir.mkdirs();

                        android.net.Uri uri = androidx.core.content.FileProvider.getUriForFile(
                            mContext, mContext.getPackageName() + ".provider", modsDir
                        );
                        intent.setDataAndType(uri, "resource/folder");
                        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        mContext.startActivity(intent);
                    } catch (Exception e) {
                        showNotice("Kivy: Opening folder with mods...");
                    }
                    break;

                case "SET_NOTCH":
                    boolean ignoreNotch = Boolean.parseBoolean(value);
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.DEFAULT_PREF.edit()
                        .putBoolean("ignoreNotch", ignoreNotch)
                        .apply();
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.PREF_IGNORE_NOTCH = ignoreNotch;
                    showNotice("Kivy: Ignore screen notch set to " + ignoreNotch);
                    break;
            }
        } catch (Exception e) {
            android.widget.Toast.makeText(mContext, "Kivy Agent Error: " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
        }
    }

    private void showNotice(String text) {
        android.widget.Toast.makeText(mContext, text, android.widget.Toast.LENGTH_SHORT).show();
            }
}
