package com.parassidhu.cdlumaths.activities;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;

public class quadratic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quadratic);
        androidx.appcompat.app.ActionBar acb = getSupportActionBar();
        acb.setHomeButtonEnabled(true);
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("Quadratic Equation Solver");
        final EditText edt1 = findViewById(R.id.a);
        final EditText edt2 = findViewById(R.id.b);
        final EditText edt3 = findViewById(R.id.c);
        final TextView root = findViewById(R.id.roots);
        int a;
        int b;
        int c;
        TextView txt = findViewById(R.id.equation);
        final Button btn = findViewById(R.id.button);
        txt.setText(Html.fromHtml("From equation ax<sup><small>2</small></sup>+bx+c:"));
        edt3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btn.callOnClick();
                }
                return false;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int a = Integer.parseInt(edt1.getText().toString());
                    int b = Integer.parseInt(edt2.getText().toString());
                    int c = Integer.parseInt(edt3.getText().toString());


                    double temp1 = Math.sqrt(b * b - 4 * a * c);

                    double root1 = (-b + temp1) / (2 * a);
                    double root2 = (-b - temp1) / (2 * a);
                    root.setText("Roots are " + root1 + " and " + root2);
                    if (root.getText().toString().contains("NaN")) {
                        root.setText(root.getText().toString() + " where NaN stands for imaginary number");
                    }
                }catch (Exception ex) {
                    Snackbar.make(findViewById(R.id.quadLayout), ex.getMessage(), Snackbar.LENGTH_LONG);
                }
            }
        });
    }
}
