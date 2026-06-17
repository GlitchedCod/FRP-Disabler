package frp.bypass;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private final int REQUEST_CODE_ENABLE_ADMIN = 1002;
    CheckBox admBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        admBtn = findViewById(R.id.device_admin_btn);

        // Set initial state of checkbox
        ComponentName deviceAdminSample = new ComponentName(this, DeviceAdmin.class);
        DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        admBtn.setChecked(mDPM.isAdminActive(deviceAdminSample));

        admBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (admBtn.isChecked()) {
                    Log.i(TAG, "Enabling device admin");
                    enableDeviceAdmin();
                } else {
                    Log.i(TAG, "Disabling device admin");
                    disableDeviceAdmin();
                }
            }
        });
    }


    private void enableDeviceAdmin() {
        ComponentName deviceAdminSample = new ComponentName(this, DeviceAdmin.class);
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdminSample);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Provide these permissions to manage the application");

        startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
    }

    private void disableDeviceAdmin() {
        DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName deviceAdminSample = new ComponentName(this, DeviceAdmin.class);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Log.i(TAG, "Removing device owner via transferOwnership");
                mDPM.transferOwnership(deviceAdminSample, null, PersistableBundle.EMPTY);
            } else {
                Log.i(TAG, "Removing device owner via clearDeviceOwnerApp");
                mDPM.clearDeviceOwnerApp(getPackageName());
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to remove device owner", e);
        }
    }
}
