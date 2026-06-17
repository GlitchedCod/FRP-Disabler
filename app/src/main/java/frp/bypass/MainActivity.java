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
import android.widget.Toast;

import java.util.List;

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ComponentName targetAdmin = findTestDpcAdmin(mDPM);

                if (targetAdmin != null) {
                    Log.i(TAG, "Removing device owner via transferOwnership to " + targetAdmin.flattenToString());
                    mDPM.transferOwnership(deviceAdminSample, targetAdmin, PersistableBundle.EMPTY);
                    Toast.makeText(this, "Ownership transferred to " + targetAdmin.getPackageName() + ".", Toast.LENGTH_LONG).show();
                } else {
                    Log.e(TAG, "Test DPC is not present as an active admin.");
                    Toast.makeText(this, "Cannot remove device owner because com.afwsamples.testdpc is not installed as an active admin.", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.i(TAG, "Removing device owner via clearDeviceOwnerApp");
                mDPM.clearDeviceOwnerApp(getPackageName());
                Toast.makeText(this, "The app was removed as the phone owner of your device.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to remove device owner", e);
            Toast.makeText(this, "Failed to remove device owner.", Toast.LENGTH_LONG).show();
        }
    }

    private ComponentName findTestDpcAdmin(DevicePolicyManager dpm) {
        List<ComponentName> activeAdmins = dpm.getActiveAdmins();
        if (activeAdmins == null) {
            return null;
        }

        for (ComponentName admin : activeAdmins) {
            if ("com.afwsamples.testdpc".equals(admin.getPackageName())) {
                return admin;
            }
        }

        return null;
    }

    private ComponentName findAlternateActiveAdmin(DevicePolicyManager dpm, ComponentName currentAdmin) {
        List<ComponentName> activeAdmins = dpm.getActiveAdmins();
        if (activeAdmins == null) {
            return null;
        }

        for (ComponentName admin : activeAdmins) {
            if (!admin.equals(currentAdmin) && !admin.getPackageName().equals(currentAdmin.getPackageName())) {
                return admin;
            }
        }

        return null;
    }
}
