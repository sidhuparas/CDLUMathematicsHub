package com.parassidhu.cdlumaths;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class Summary extends Fragment {

    public Summary() {}
    Button show;
    EditText start,end,code;
    TextView codehint;
    MaterialSpinner spinner,total;
    Intent intent;
    long val;
    long val2;
    RadioButton five,two;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    public boolean checkEntry(){
        if (start.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Starting roll number must not be blank!", Toast.LENGTH_SHORT).show(); return false;}
        if (end.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Ending roll number must not be blank!", Toast.LENGTH_SHORT).show();
            return false;}
        try {
            val = Long.valueOf(start.getText().toString());
            val2 = Long.valueOf(end.getText().toString());
        }catch (Exception e){
            Toast.makeText(getActivity(), "Please input correct values!", Toast.LENGTH_SHORT).show(); return false;
        }
        if (val2-val>70){
            Toast.makeText(getActivity(), "Maximum 70 results can be produced. Please modify the range.", Toast.LENGTH_SHORT).show(); return false;
        }
        if (val2-val==0){
            Toast.makeText(getActivity(), "Please enter a valid range!", Toast.LENGTH_SHORT).show(); return false;
        }
        if (val2<val) {
            Toast.makeText(getActivity(), "Starting roll number must be smaller!", Toast.LENGTH_SHORT).show(); return false;
        }
        if (code.getVisibility()==View.VISIBLE) {
            if (code.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Please enter a subject code", Toast.LENGTH_SHORT).show(); return false;
            }
        }
        return true;
    }

    public void onViewCreated(View view, Bundle savedInstance){
        super.onViewCreated(view, savedInstance);
        show = getActivity().findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEntry()) {
                    val=Long.valueOf(start.getText().toString());
                    val2=Long.valueOf(end.getText().toString());
                    intent = new Intent(getActivity(), SummaryResult.class);
                    if (five.isChecked())
                     intent.putExtra("code", 340 + (spinner.getText().toString()));
                    else
                        intent.putExtra("code", 140 + (spinner.getText().toString()));

                    intent.putExtra("start", val);
                    intent.putExtra("end", val2);
                    intent.putExtra("sem",spinner.getText().toString());
                    if (total.getSelectedIndex()==0) intent.putExtra("which","total"); else intent.putExtra("which",code.getText().toString());
                    startActivity(intent);
                }
            }
        });
        spinner = getActivity().findViewById(R.id.spinner);
        total = getActivity().findViewById(R.id.total);
        code = getActivity().findViewById(R.id.code);
        codehint = getActivity().findViewById(R.id.codehint);
        codehint.setVisibility(View.GONE);
        codehint.setText("For example, "+"\"MH54\","+"\"MH24\".");
        spinner.setItems("1","2","3","4","5","6","7","8","9","10");
        total.setItems("Total Marks","Specific Subject");
        total.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position == 1) {
                    code.setVisibility(View.VISIBLE);
                    codehint.setVisibility(View.VISIBLE);
                } else {
                    code.setVisibility(View.GONE);
                    codehint.setVisibility(View.GONE);
                }

            }
        });
        start = getActivity().findViewById(R.id.txtSem2);
        end = getActivity().findViewById(R.id.txtSem3);
        five= getActivity().findViewById(R.id.five);
        two= getActivity().findViewById(R.id.two);
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setItems("1","2","3","4","5","6","7","8","9","10");
                two.setChecked(false);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setItems("1","2","3","4");
                five.setChecked(false);
            }
        });

        start.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (end.length() <= 8) {
                        end.setText(start.getText());
                    }
                }catch (Exception e){}
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}