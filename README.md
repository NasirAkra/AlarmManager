# AlarmManager in Android

The **AlarmManager** is a system service in Android that allows you to schedule tasks to run at specific times or intervals. It is particularly useful for setting up alarms, reminders, or periodic tasks that need to execute even when the app is not running.

---

## Key Features

- **Scheduled Execution**: Run tasks at exact times or intervals.
- **Wake Device**: Can wake up the device if it is in a low-power state (using `RTC_WAKEUP` or `ELAPSED_REALTIME_WAKEUP`).
- **Work in Background**: Ensures tasks execute even when the app is closed.
- **Repeating Alarms**: Supports setting recurring tasks.

---

## Basic Usage

### Setting a One-Time Alarm

Here is how to schedule a one-time alarm using `AlarmManager`:

```java
AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
Intent intent = new Intent(context, AlarmReceiver.class);
PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

// Schedule the alarm
long triggerTime = System.currentTimeMillis() + 60000; // 1 minute from now
alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
```

---

## Example: Setting a Repeating Alarm

### Code:

```java
AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
Intent intent = new Intent(context, AlarmReceiver.class);
PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

// Schedule the repeating alarm
long interval = AlarmManager.INTERVAL_HOUR; // Repeat every hour
long startTime = System.currentTimeMillis();
alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, pendingIntent);
```

### AlarmReceiver Class:

```java
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Code to execute when alarm triggers
        Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_SHORT).show();
    }
}
```

---

## Handling Alarms on Device Boot

To reschedule alarms after a device reboot, add the `BOOT_COMPLETED` broadcast receiver:

### Manifest Declaration:

```xml
<receiver android:name=".BootReceiver">
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>
</receiver>

<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
```

### BootReceiver Class:

```java
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Reschedule alarms here
        }
    }
}
```

---

## Types of Alarms

1. **RTC (Real Time Clock)**:
   - Triggers based on the system clock.
   - Examples: `AlarmManager.RTC_WAKEUP`, `AlarmManager.RTC`.

2. **Elapsed Real Time**:
   - Triggers based on time since the device booted.
   - Examples: `AlarmManager.ELAPSED_REALTIME_WAKEUP`, `AlarmManager.ELAPSED_REALTIME`.

---

## Best Practices

- **Avoid Exact Repeating Alarms**: Use `setExactAndAllowWhileIdle` for precise one-time alarms instead.
- **Use `WorkManager` for Long-Running Tasks**: If tasks need to run in the background reliably, consider using `WorkManager` instead of `AlarmManager`.
- **Battery Optimization**: Be cautious with frequent alarms, as they can impact battery life.
- **Test on Different Devices**: Behavior may vary across different Android versions.

---

## Resources

- [AlarmManager Documentation](https://developer.android.com/reference/android/app/AlarmManager)
- [Background Execution Limits](https://developer.android.com/about/versions/oreo/background)
- [WorkManager vs AlarmManager](https://developer.android.com/topic/libraries/architecture/workmanager)

---
