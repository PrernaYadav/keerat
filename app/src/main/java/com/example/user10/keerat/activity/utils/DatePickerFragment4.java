package com.example.user10.keerat.activity.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.user10.keerat.R;

import java.util.Calendar;

/**
 * Created by designer on 11/23/2017.
 */

public class DatePickerFragment4 extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        TextView textView= (TextView) getActivity().findViewById(R.id.tv_aid_date);
        textView.setText(+view.getDayOfMonth()+"/"+view.getMonth()+"/"+view.getYear());

    }
}
