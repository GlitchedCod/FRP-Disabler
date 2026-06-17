package frp.bypass;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.app.admin.FactoryResetProtectionPolicy;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DeviceAdmin extends DeviceAdminReceiver {

    private static final String TAG = DeviceAdmin.class.getName();

    private void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
        super.onEnabled(context, intent);
        showToast(context, "Device admin enabled");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            if (dpm != null && dpm.isDeviceOwnerApp(context.getPackageName())) {
                Log.i(TAG, "Setting FRP disabled as device owner");
                FactoryResetProtectionPolicy frpPolicy = new FactoryResetProtectionPolicy.Builder()
                        .setFactoryResetProtectionEnabled(false)
                        .build();
                ComponentName adminComponent = new ComponentName(context, DeviceAdmin.class);
                dpm.setFactoryResetProtectionPolicy(adminComponent, frpPolicy);
            }
        }
    }

    @Nullable
    @Override
    public CharSequence onDisableRequested(@NonNull Context context, @NonNull Intent intent) {
        showToast(context, "Device admin disable requested");
        return super.onDisableRequested(context, intent);
    }

    @Override
    public void onDisabled(@NonNull Context context, @NonNull Intent intent) {
        super.onDisabled(context, intent);
        showToast(context, "Device admin disabled");
    }
}
