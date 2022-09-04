package com.sireph.technics.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DateTimeInput {
    @SuppressLint("DefaultLocale")
    public static void setupTimeInput(EditText editText, Context context) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        editText.setRawInputType(InputType.TYPE_NULL);
        editText.setFocusable(true);
        editText.setText(String.format("%02d:%02d", currentHour, currentMinute));
        editText.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, (timePicker, hourOfDay, minutes) -> {
                editText.setText(String.format("%02d:%02d", hourOfDay, minutes));
            }, currentHour, currentMinute, true);
            timePickerDialog.show();
        });
    }

    @SuppressLint("DefaultLocale")
    public static void setupDateInput(EditText editText, Context context) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        editText.setRawInputType(InputType.TYPE_NULL);
        editText.setFocusable(true);
        editText.setText(String.format("%02d/%02d/%04d", currentDayOfMonth, currentMonth + 1, currentYear));
        editText.setOnClickListener(view -> {
            @SuppressLint("SetTextI18n")
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    (datePicker, year, month, day) -> {
                        editText.setText(String.format("%02d/%02d/%04d", day, (month + 1), year));
                    }, currentYear, currentMonth, currentDayOfMonth);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
    }
}
