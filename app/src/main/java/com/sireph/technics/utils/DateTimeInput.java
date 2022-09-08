package com.sireph.technics.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.sireph.technics.R;

import java.util.Calendar;

public class DateTimeInput {
    @SuppressLint("DefaultLocale")
    public static EditText setupTimeInput(View included, Context context) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        EditText text = included.findViewById(R.id.timeText);
        Button button = included.findViewById(R.id.timeButton);

        text.setText(String.format("%02d:%02d", currentHour, currentMinute));
        button.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, (timePicker, hourOfDay, minutes) -> {
                text.setText(String.format("%02d:%02d", hourOfDay, minutes));
            }, currentHour, currentMinute, true);
            timePickerDialog.show();
        });

        return text;
    }

    @SuppressLint("DefaultLocale")
    public static EditText setupDateInput(View included, Context context) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        EditText text = included.findViewById(R.id.dateText);
        Button button = included.findViewById(R.id.dateButton);

        text.setText(String.format("%02d/%02d/%04d", currentDayOfMonth, currentMonth + 1, currentYear));
        button.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    (datePicker, year, month, day) -> {
                        text.setText(String.format("%02d/%02d/%04d", day, (month + 1), year));
                    }, currentYear, currentMonth, currentDayOfMonth);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        return text;
    }
}
