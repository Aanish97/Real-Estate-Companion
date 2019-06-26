package com.example.rec;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class createAccountPg extends AppCompatActivity {
    String usrN;
    String pass;
    String Rpass;
    String name;
    String DOB;
    String address;
    String contact;

    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_pg);

        progressDialog = new ProgressDialog(this);
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        myCalendar = Calendar.getInstance();

        final EditText edittext= (EditText) findViewById(R.id.edtDOB);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(edittext, myCalendar);
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(createAccountPg.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(EditText edittext, Calendar myCalendar) {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    public void onbtnClick(View v) {
        EditText edt1 = this.findViewById(R.id.edtUsr);
        EditText edt2 = this.findViewById(R.id.edtPass);
        EditText edt3 = this.findViewById(R.id.edtRePass);
        EditText edt4 = this.findViewById(R.id.edtName);
        EditText edt5 = this.findViewById(R.id.edtDOB);
        EditText edt6 = this.findViewById(R.id.edtAddress);
        EditText edt7 = this.findViewById(R.id.edtContact);

        usrN = edt1.getText().toString();
        pass = edt2.getText().toString();
        Rpass = edt3.getText().toString();
        name = edt4.getText().toString();
        DOB = edt5.getText().toString();
        address = edt6.getText().toString();
        contact = edt7.getText().toString();

        if(pass.equals(Rpass))
        {
            if (usrN.isEmpty() || pass.isEmpty() || Rpass.isEmpty() || name.isEmpty() || DOB.isEmpty() || address.isEmpty() || contact.isEmpty()) {
                Toast.makeText(this, "Please fill in all the credentials", Toast.LENGTH_LONG).show();
            } else {
                progressDialog = new ProgressDialog(this);
                registerUserFirebase();//usrN, pass, Rpass, name, DOB, address, contact);
            }
        }
        else
        {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show();
        }
    }

    private void registerUserFirebase() {

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(usrN)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(usrN, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //adding account creds to firebase database
                            //getting current user
                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            //getting uid of current user
                            String uid = current_user.getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("DOB", DOB);
                            userMap.put("address", address);
                            userMap.put("contact", contact);
                            userMap.put("password", pass);
                            userMap.put("name", name);
                            userMap.put("username", usrN);

                            mDatabase.setValue(userMap);

                            //display some message here
                            Toast.makeText(createAccountPg.this, "Successfully registered", Toast.LENGTH_LONG).show();
                            Intent i1 = new Intent(createAccountPg.this, homePg.class);
                            createAccountPg.this.startActivity(i1);
                            finish();
                            //startActivity(new Intent(getApplicationContext(), homePg.class));
                        } else {
                            //display some message here
                            Toast.makeText(createAccountPg.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }

                        progressDialog.dismiss();
                    }
                });

    }

/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        EditText edt1 = this.findViewById(R.id.edtUsr);
        EditText edt2 = this.findViewById(R.id.edtPass);
        EditText edt3 = this.findViewById(R.id.edtRePass);
        EditText edt4 = this.findViewById(R.id.edtName);
        EditText edt5 = this.findViewById(R.id.edtDOB);
        EditText edt6 = this.findViewById(R.id.edtAddress);
        EditText edt7 = this.findViewById(R.id.edtContact);

        outState.putString("usrN", edt1.getText().toString());
        outState.putString("pass", edt2.getText().toString());
        outState.putString("Rpass", edt3.getText().toString());
        outState.putString("name", edt4.getText().toString());
        outState.putString("DOB", edt5.getText().toString());
        outState.putString("address", edt6.getText().toString());
        outState.putString("contact", edt7.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        EditText edt1 = this.findViewById(R.id.edtUsr);
        EditText edt2 = this.findViewById(R.id.edtPass);
        EditText edt3 = this.findViewById(R.id.edtRePass);
        EditText edt4 = this.findViewById(R.id.edtName);
        EditText edt5 = this.findViewById(R.id.edtDOB);
        EditText edt6 = this.findViewById(R.id.edtAddress);
        EditText edt7 = this.findViewById(R.id.edtContact);

        usrN = savedInstanceState.getString("usrN");
        pass = savedInstanceState.getString("pass");
        Rpass = savedInstanceState.getString("Rpass");
        name = savedInstanceState.getString("name");
        DOB = savedInstanceState.getString("DOB");
        address = savedInstanceState.getString("address");
        contact = savedInstanceState.getString("contact");

        edt1.setText(usrN);
        edt2.setText(pass);
        edt3.setText(Rpass);
        edt4.setText(name);
        edt5.setText(DOB);
        edt6.setText(address);
        edt7.setText(contact);
    }
*/

}
