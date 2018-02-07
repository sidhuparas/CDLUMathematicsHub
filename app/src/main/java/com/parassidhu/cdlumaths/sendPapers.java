package com.parassidhu.cdlumaths;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sendPapers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_papers);
        android.support.v7.app.ActionBar acb = getSupportActionBar();
        acb.setHomeButtonEnabled(true);
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("Send Question Papers");
        Button btn = findViewById(R.id.btn);
        final EditText name = findViewById(R.id.name);
        final EditText detail = findViewById(R.id.detail);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().length()==0 || detail.getText().length()==0){
                    Toast.makeText(sendPapers.this, "Please fill the given fields.", Toast.LENGTH_SHORT).show();
                }else {


                    String[] TO = {"parassidhu@downloadinformer.com"};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("message/rfc822");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Question Papers From " + name.getText());
                    emailIntent.putExtra(Intent.EXTRA_TEXT, detail.getText());

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(sendPapers.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
