package com.example.user10.keerat.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user10.keerat.R;

public class SelectionForm extends AppCompatActivity {
   Spinner spcat;
   String type;
    private Button btnsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_form);
        spcat=(Spinner)findViewById(R.id.sp_cat);

        spcat.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spcat.getSelectedView()).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });



        spcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = spcat.getSelectedItem().toString();

                if (type.contentEquals("Vendor")){
                    Intent intent = new Intent(SelectionForm.this,SignupActivity.class);
                intent.putExtra("typ",type);
                startActivity(intent);
                Toast.makeText(SelectionForm.this,type,Toast.LENGTH_SHORT).show();
                }else if (type.contentEquals("Gurudwara")){
                    Intent intent = new Intent(SelectionForm.this,SignupActivity.class);
                    intent.putExtra("typ",type);
                    startActivity(intent);
                }else {
                    Toast.makeText(SelectionForm.this,"Please Select Category",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
}
