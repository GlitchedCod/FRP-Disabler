# Android Device Admin sample app

Sample application for enabling/disabling device admin from UI or shell.

## Steps for enabling/disabling device admin
Below steps shows hot to enable or disable device admin. This has to be done by the user and user can disable it if they can. If you don't want user to disable device admin it can be done using adb.

### Using adb

```sh
adb shell
# Enable device admin
dpm set-device-owner --user 0 frp.bypass/.DeviceAdmin

# Disable device admin
dpm remove-active-admin --user 0 frp.bypass/.DeviceAdmin
```

### Setup using User & UI
1. Create a receiver
```xml  
    <receiver
        android:name=".DeviceAdmin"
        android:enabled="true"
        android:exported="true"
        android:permission="android.permission.BIND_DEVICE_ADMIN">
        <meta-data
            android:name="android.app.device_admin"
            android:resource="@xml/device_admin_sample" />

        <intent-filter>
            <action android:name="android.app.action.DEVICE_ADMIN_ENABLED" />
            <action android:name="android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED" />
            <action android:name="android.app.action.DEVICE_ADMIN_DISABLED" />
        </intent-filter>
    </receiver>
```
2. Extend the class with DeviceAdminReceiver and override methods if you want

```java
public class DeviceAdmin extends DeviceAdminReceiver {

    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
        super.onEnabled(context, intent);
        showToast(context, "Device admin enabled");
    }
}
```
3. Enable device admin
```java
private void enableDeviceAdmin() {
    ComponentName deviceAdminSample = new ComponentName(this, DeviceAdmin.class);
    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdminSample);
    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Provide these permissions to manage the application");

    startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
}
```
4. or disable
```java
 private void disableDeviceAdmin() {
    DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
    ComponentName deviceAdminSample = new ComponentName(this, DeviceAdmin.class);
    mDPM.removeActiveAdmin(deviceAdminSample);
}
```
