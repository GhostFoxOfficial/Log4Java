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
                    
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.DEFAULT_PREF.edit()
                        .putString("username", value)
                        .apply();
                    
                    
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.loadPreferences(mContext);
                    
                    showNotice("Kivy: Player name has been changed to " + value);
                    break;

                case "SET_RAM":
                    int ramMb = Integer.parseInt(value);
                    
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.DEFAULT_PREF.edit()
                        .putInt("ramAllocation", ramMb)
                        .apply();
                    
                    net.kdt.pojavlaunch.prefs.LauncherPreferences.PREF_RAM_ALLOCATION = ramMb;
                    
                    showNotice("Kivy: Changed RAM to " + ramMb + " MB");
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
                        }
        } catch (Exception e) {
            Toast.makeText(mContext, "Kivy Agent Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showNotice(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
                           }

