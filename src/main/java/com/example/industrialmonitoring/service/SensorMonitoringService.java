package com.example.industrialmonitoring.service;

import com.example.industrialmonitoring.model.Alarm;
import com.example.industrialmonitoring.model.Sensor;
import com.example.industrialmonitoring.model.SensorReading;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SensorMonitoringService {
    private final List<SensorReading> readings = new ArrayList<>();
    private final List<Alarm> alarms = new ArrayList<>();

    public SensorReading addReading(Sensor sensor, double value) {
        SensorReading reading = new SensorReading(sensor, value);
        readings.add(reading);

        if (!reading.isNormal()) {
            Alarm alarm = createAlarm(reading);
            alarms.add(alarm);
        }

        return reading;
    }

    public List<SensorReading> getReadings() {
        return readings;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void displayReadingStatus(SensorReading reading) {
        Sensor sensor = reading.getSensor();

        System.out.println("\nSensor: " + sensor.getSensorId() + " - " + sensor.getName());
        System.out.println("Type: " + sensor.getType());
        System.out.println("Value: " + reading.getValue() + " " + sensor.getUnit());
        System.out.println("Normal range: " + sensor.getMinimumAllowedValue()
                + " - " + sensor.getMaximumAllowedValue() + " " + sensor.getUnit());

        if (reading.isNormal()) {
            System.out.println("Status: NORMAL");
        } else {
            System.out.println("Status: ALARM");
            System.out.println("Alarm: " + buildAlarmMessage(reading));
        }
    }

    public void displayAlarmHistory() {
        System.out.println("\n===== ALARM HISTORY =====");

        if (alarms.isEmpty()) {
            System.out.println("No alarms detected.");
            return;
        }

        alarms.forEach(System.out::println);
    }

    private Alarm createAlarm(SensorReading reading) {
        String alarmId = "AL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return new Alarm(
                alarmId,
                reading.getSensor(),
                reading.getValue(),
                buildAlarmMessage(reading)
        );
    }

    private String buildAlarmMessage(SensorReading reading) {
        Sensor sensor = reading.getSensor();
        double value = reading.getValue();

        if (value < sensor.getMinimumAllowedValue()) {
            return sensor.getName() + " is below the minimum allowed value.";
        }

        return sensor.getName() + " is above the maximum allowed value.";
    }
}