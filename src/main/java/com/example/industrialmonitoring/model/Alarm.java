package com.example.industrialmonitoring.model;

import java.time.LocalDateTime;

public class Alarm {
    private String alarmId;
    private Sensor sensor;
    private double actualValue;
    private String message;
    private LocalDateTime timestamp;

    public Alarm(String alarmId, Sensor sensor, double actualValue, String message) {
        this.alarmId = alarmId;
        this.sensor = sensor;
        this.actualValue = actualValue;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public double getActualValue() {
        return actualValue;
    }

    public void setActualValue(double actualValue) {
        this.actualValue = actualValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ALARM ID: " + alarmId
                + ", Sensor: " + sensor.getSensorId()
                + " - " + sensor.getName()
                + ", Actual value: " + actualValue + " " + sensor.getUnit()
                + ", Message: " + message
                + ", Time: " + timestamp;
    }
}