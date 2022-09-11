package com.sireph.technics.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sireph.technics.R;
import com.sireph.technics.models.date.DateTime;

import java.util.Calendar;

public class DateTimeInput {
    @SuppressLint("DefaultLocale")
    public static EditText setupTimeInput(View included, Context context, boolean isActive, boolean fillCurrent, DateTime value, boolean allowEmpty) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        EditText text = included.findViewById(R.id.timeText);
        Button button = included.findViewById(R.id.timeButton);

        if (value != null) {
            text.setText(value.format("hh:mm"));
        }
        if (isActive) {
            if (fillCurrent && value == null) {
                text.setText(String.format("%02d:%02d", currentHour, currentMinute));
            }
            text.addTextChangedListener(new TextChangedWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!Validation.validateTime(s.toString(), allowEmpty)) {
                        text.setError(context.getString(R.string.invalid_time));
                    }
                }
            });
            button.setOnClickListener(view -> {
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, (timePicker, hourOfDay, minutes) -> text.setText(String.format("%02d:%02d", hourOfDay, minutes)), currentHour, currentMinute, true);
                timePickerDialog.show();
            });
        } else {
            text.setEnabled(false);
            button.setEnabled(false);
        }

        return text;
    }

    @SuppressLint("DefaultLocale")
    public static EditText setupDateInput(View included, Context context, boolean isActive, boolean fillCurrent, DateTime value, boolean allowEmpty) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        EditText text = included.findViewById(R.id.dateText);
        Button button = included.findViewById(R.id.dateButton);

        if (value != null) {
            text.setText(value.format("dd/MM/yyyy"));
        }
        if (isActive) {
            if (fillCurrent && value == null) {
                text.setText(String.format("%02d/%02d/%04d", currentDayOfMonth, currentMonth + 1, currentYear));
            }
            text.addTextChangedListener(new TextChangedWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!Validation.validateDate(s.toString(), allowEmpty)) {
                        text.setError(context.getString(R.string.invalid_date));
                    }
                }
            });
            button.setOnClickListener(view -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        (datePicker, year, month, day) -> text.setText(String.format("%02d/%02d/%04d", day, (month + 1), year)), currentYear, currentMonth, currentDayOfMonth);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            });
        } else {
            text.setEnabled(false);
            button.setEnabled(false);
        }

        return text;
    }
}
