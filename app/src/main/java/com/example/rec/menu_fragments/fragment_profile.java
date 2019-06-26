package com.example.rec.menu_fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rec.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.AlertDialog;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class fragment_profile extends Fragment {

    ImageView backBtn;

    //tayyab variables
    private Button btn;
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    private static final String TAG = "MainActivity";
    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //aanish variables(firebase)
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtName;
    private EditText edtDOB;
    private EditText edtAddress;
    private EditText edtContact;
    private String username;
    private String password;
    private String name;
    private String DOB;
    private String address;
    private String contact;
    private Button btnUpdate;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.fragment_settings_actionbarlayout);

        View viewX = ((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView();

        backBtn = (ImageView) viewX.findViewById(R.id.imgBack);

        TextView textView =(TextView)viewX.findViewById(R.id.heading);
        textView.setText("Profile");

        edtUsername = (EditText)((AppCompatActivity)getActivity()).findViewById(R.id.edtuser);
        edtPassword = (EditText)((AppCompatActivity)getActivity()).findViewById(R.id.edtpass);
        edtName = (EditText)((AppCompatActivity)getActivity()).findViewById(R.id.edtname);
        edtDOB = (EditText)((AppCompatActivity)getActivity()).findViewById(R.id.edtDOB);
        edtAddress = (EditText)((AppCompatActivity)getActivity()).findViewById(R.id.edtaddress);
        edtContact = (EditText)((AppCompatActivity)getActivity()).findViewById(R.id.edtcontaxt);


        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_home()).commit();
            }
        });
        //setting editText from firebase
        progressDialog = new ProgressDialog(((AppCompatActivity)getActivity()));
        progressDialog.setTitle("Fetching User Info");
        progressDialog.setMessage("Please wait while we retrieve your information");
        progressDialog.show();

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username = dataSnapshot.child("username").getValue().toString();
                password = dataSnapshot.child("password").getValue().toString();
                name = dataSnapshot.child("name").getValue().toString();
                DOB = dataSnapshot.child("DOB").getValue().toString();
                address = dataSnapshot.child("address").getValue().toString();
                contact = dataSnapshot.child("contact").getValue().toString();

                edtUsername.setText(username);
                edtPassword.setText(password);
                edtName.setText(name);
                edtDOB.setText(DOB);
                edtAddress.setText(address);
                edtContact.setText(contact);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //tayyab code
        //calender
        calender();


        //onclicklistener of the update button
        // for image change
        btn = (Button) ((AppCompatActivity)getActivity()).findViewById(R.id.btnForImage);
        imageview = (ImageView) ((AppCompatActivity)getActivity()).findViewById(R.id.circularImageView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        btnUpdate = (Button) ((AppCompatActivity)getActivity()).findViewById(R.id.button);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(((AppCompatActivity)getActivity()));
                progressDialog.setTitle("Updating User Profile");
                progressDialog.setMessage("Please wait while we update your profile");
                progressDialog.show();

                edtUsername = (EditText)((AppCompatActivity)getActivity()).findViewById(R.id.edtuser);
                edtPassword = (EditText)((AppCompatActivity)getActivity()).findViewById(R.id.edtpass);
                edtName = (EditText)((AppCompatActivity)getActivity()).findViewById(R.id.edtname);
                edtDOB = (EditText)((AppCompatActivity)getActivity()).findViewById(R.id.edtDOB);
                edtAddress = (EditText)((AppCompatActivity)getActivity()).findViewById(R.id.edtaddress);
                edtContact = (EditText)((AppCompatActivity)getActivity()).findViewById(R.id.edtcontaxt);
                username = edtUsername.getText().toString();
                password = edtPassword.getText().toString();
                name = edtName.getText().toString();
                DOB = edtDOB.getText().toString();
                address = edtAddress.getText().toString();
                contact = edtContact.getText().toString();

                mUserDatabase.child("username").setValue(username);
                mUserDatabase.child("password").setValue(password);
                mUserDatabase.child("name").setValue(name);
                mUserDatabase.child("DOB").setValue(DOB);
                mUserDatabase.child("address").setValue(address);
                mUserDatabase.child("Contact").setValue(contact);

                ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_home()).commit();
            }
        });
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(((AppCompatActivity)getActivity()));
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ((AppCompatActivity)getActivity()).RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(((AppCompatActivity)getActivity()).getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(((AppCompatActivity)getActivity()), "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(((AppCompatActivity)getActivity()), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(((AppCompatActivity)getActivity()), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(((AppCompatActivity)getActivity()),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    void calender(){
        mDisplayDate = (EditText) ((AppCompatActivity)getActivity()).findViewById(R.id.edtDOB);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ((AppCompatActivity)getActivity()),
                        android.R.style.Theme_Black_NoTitleBar_Fullscreen,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }

}
