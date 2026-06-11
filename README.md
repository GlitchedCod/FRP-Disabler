# FRP Bypass

Sample application for disabling FRP security for device that aren't already locked.

## Why using this app ?

By default on modern Android devices if you add a password or a Google account onto an Android device and you wipe it in the recovery mode it will still ask you for your password or your Google account. This makes your device a brick.

If you forgot your password, you're out of luck and if the warranty ended you now have a very nice brick.

Even if your phone is being stolen, at this point why Google should break your device ? If the one who stole it wipe it then your personal data is already safe, FRP protection doesn't block malicious access to your data and just allows you to brick the device and asking for a password. It doesn't even send your location to Google to help you retrieve the phone and it doesn't show to the one who wiped the phone how to contact you to even send you back your device.

## How does it work ?
The only settings that can disable FRP lock before it's too late is to have a MDM device manager app that is set as the "device owner" of your phone (in the "Administrator" app in security settings on an Android device).

This is a tool for big and medium companies, but for the masses it's something unheard of.

This app can be set as the device owner so this will disable FRP and you won't have to worry about it anymore.

You'll need an Android device with developer settings enabled and USB debugging / Wireless debugging enabled and a computer to run the "device owner" command.

# Steps for enabling FRP Bypass
Below steps shows hot to enable or disable FRP Bypass to be the device owner (admin). This has to be done by the user and user can disable it if they can. If you don't want user to disable device admin it can be done using adb.

## Using adb

```sh
# Enable device admin
adb shell dpm set-device-owner --user 0 frp.bypass/.DeviceAdmin
```

# Steps for disabling FRP Bypass

## Using adb

```sh
# Disable device admin
adb shell dpm remove-active-admin --user 0 frp.bypass/.DeviceAdmin
```

## Using dial
With your phone app you can dial `*#*#377#*#*` to launch the app. Some third party phone apps can't do that, you can use the Google Dial app if the one you're using doesn't work.

## Using Activity Launcher
You can use [Activity Launcher](https://f-droid.org/en/packages/de.szalkowski.activitylauncher/) from F-Droid or from the Play Store. With that you can launch the app and disable it.

# Q&A
## My phone is already FRP locked, can I use this app ?

No, maybe eventually if you can enable ADB and install the app and set it as device owner but generally when you have access to ADB in a FRP locked state you can already wipe the Google Play Service data or launch the settings to add another passwords.

## So what's the point ?

When you buy a new phone you can install this app at first, even before the Google account (or else you have to remove the Google account before being able to enable this app) so that if you forget your password or your Google account being compromised you can still wipe it from the recovery and configure it from scratch !

I recommend you to install it onto the phones of your friends or your family member when they get a new Android device. You'll never known 

## I got the error "Not allowed to set the device owner because there are already several users on the device"

Your phone needs to have no account at all if you want to set this app as the device owner to block FRP protection.

If you got tons of account like email, messaging and stuff you'll have to remove all of these accounts from your phone and disable some apps that sometimes decide that you can't remove their account.

You will loose app data and messages by doing this !

If you really want to install this app I recommend [this XDA guide](https://xdaforums.com/t/how-i-got-device-owner-without-factory-reset.4745834/) to temporary disable all apps  with an account but it will still wipe all the data of these apps.

I really recommend installing this app on a brand new device and skipping the Google account so that you don't have to remove the account from your device, install the app and then loggin in again.

## I got issues with my Xiaomi / Redmi / Poco phone

I've tested it onto a Xiaomi Redmi 9C NFC, you need to create / connect a Xiaomi account to your phone, add a SIM card to activate the device. In the developer settings enable "Install via USB" and "USB Debugging (security settings)". After that you can remove your Xiaomi account from your device to set the app as the owner (there should be no account on the device when the app is set as the owner).

## It is vibecoded ?

It's a fork of a real Android app project that has been edited and vibecoded by Copilot. I know it's not the good thing to do. The main goal was to have an open source app that I can trust to set it as the device owner, the app is really simple so you can understand how does it work and see if there is any shady codes. A device administrator grand all kinds of permissions but a "device owner" app is even worse so I don't wanted to set any app as the device owner.

I don't have the time or the motivation to learn all of that just to save a device from being bricked by a member of the GAFAM named Google. With all of that in mind I think that if the app save at least 1 smartphone from being a brick so approximatively at worst 1kg of CO2 to vibecoding this project versus at best 25kg of CO2 from making a brand new smartphone. If I hadn't this in mind I wouldn't have made this project.

Also everything that I know how to do like writing, making a logo, Copilot didn't helped me for that. So all of this text is 100% human-made. I only ask Copilot to fix the things that I don't know how to make.

# Licence
MIT License just like the original projet.