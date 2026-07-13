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

        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (dpm != null && dpm.isDeviceOwnerApp(context.getPackageName())) {
            ComponentName adminComponent = new ComponentName(context, DeviceAdmin.class);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Log.i(TAG, "Setting device owner support messages");
                dpm.setShortSupportMessage(adminComponent,
                        context.getString(R.string.device_admin_short_support_message));
                dpm.setLongSupportMessage(adminComponent,
                        context.getString(R.string.device_admin_long_support_message));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Log.i(TAG, "Setting FRP Disabler as device owner");
                FactoryResetProtectionPolicy frpPolicy = new FactoryResetProtectionPolicy.Builder()
                        .setFactoryResetProtectionEnabled(false)
                        .build();
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
