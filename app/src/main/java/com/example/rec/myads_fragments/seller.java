package com.example.rec.myads_fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rec.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class seller extends Fragment implements RecyclerItemTouch.OnItemClickListener{

    private ImageView btn;
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    DatabaseReference mUserDatabase;
    String current_uid;
    private static final String TAG = "MainActivity";
    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    DatabaseReference reference;
    ProductAdapter adapter;

    private static seller single_instance = null;
    Dialog myDialog;
    Dialog myDialog1;
    Spinner Site;
    int iop=1;
    ArrayList<aanishPRoduct> aanishbhai;

    public static seller getInstance()
    {
        //if (single_instance == null)
        //single_instance = new seller();

        return single_instance;
    }
    //a list to store all the products
    String uidz;
    public List<product> productList;

    //the recyclerview
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.seller, container, false);

        myDialog=new Dialog(this.getContext());
        myDialog1=new Dialog(this.getContext());
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //initializing the productlist
        productList = new ArrayList<>();

        aanishbhai=new ArrayList<aanishPRoduct>();

        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        current_uid = mCurrentUser.getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("myAds");
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    uidz = dataSnapshot1.child("uid").getValue().toString();
                    String type = dataSnapshot1.child("type").getValue().toString();
                    if(current_uid.equals(uidz) && type.equals("seller"))
                    {
                        aanishPRoduct l = dataSnapshot1.getValue(aanishPRoduct.class);
                        aanishbhai.add(l);
                    }

                }
                //System.out.println("ye aray list ki length hai   " + aanishbhai.size());
                //adding some items to our list
                for(int i=0;i<aanishbhai.size()-1;i++)
                {
                    System.out.println("kya ha hai");
                    productList.add(new product( i,aanishbhai.get(i).getLocation(),
                            aanishbhai.get(i).getDescription(),
                            R.drawable.profile_icon));iop++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        //creating recyclerview adapter
        adapter = new ProductAdapter(this.getContext(), productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemTouch(this.getContext(),recyclerView,this));


        v.findViewById(R.id.postAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupForPostAdd();
            }
        });

        return v;

    }

    void add_product(product p){
        //productList.add(new product(p.getId(),p.getLocation(),p.getDescription(),p.getImage()));
        //adapter.notifyDataSetChanged();
    }

    void postAdd(){


        //Toast.makeText(this.getContext(),"Post Add...",Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(getActivity(), postadd.class);
        //getActivity().startActivity(intent);

    }



    @Override
    public void onItemClick(View view, int position) {

        //Toast.makeText(this.getContext(),productList.get(position).getLocation(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLongItemClick(View view, final int position) {

        //Toast.makeText(this.getContext(),productList.get(position).getLocation(),Toast.LENGTH_SHORT).show();
        showPopup(position);

    }

    public void showPopupForPostAdd(){
        myDialog1.setContentView(R.layout.postadd);



        btn = (ImageView) myDialog1.findViewById(R.id.imageView2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });




        Site = myDialog1.findViewById(R.id.siteType);
        List<String> list1 = new ArrayList<String>();
        list1.add("House");
        list1.add("Shop");
        list1.add("Plaza");
        list1.add("Petrol Pump");
        list1.add("Flat/Apartment");
        list1.add("Other");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(myDialog1.getContext(),android.R.layout.simple_spinner_item, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Site.setAdapter(dataAdapter1);

        Button post;
        //Toast.makeText(this.getContext(),productList.get(position).getLocation(),Toast.LENGTH_SHORT).show();
        post = (Button) myDialog1.findViewById(R.id.postButton);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                EditText location1= (myDialog1.findViewById(R.id.location));
                EditText description1= myDialog1.findViewById(R.id.description);
                Spinner siteType1 = (myDialog1.findViewById(R.id.siteType));
                EditText area1 = (myDialog1.findViewById(R.id.area));
                EditText demand1 = (myDialog1.findViewById(R.id.demand));

                String location = location1.getText().toString();
                String description = description1.getText().toString();
                String siteType = siteType1.getSelectedItem().toString();
                String area = area1.getText().toString();
                String demand = demand1.getText().toString();

                //firebase wala kaam hora hai
                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                //getting uid of current user
                String uid = current_user.getUid();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("uid", uid);
                userMap.put("area", area);
                userMap.put("demand", demand);
                userMap.put("siteType", siteType);
                userMap.put("description", description);
                userMap.put("location", location);
                userMap.put("type", "seller");

                reference.child("myAds").push().setValue(userMap);

                productList.add(new product(iop,location,description,R.drawable.profile_icon));
                myDialog1.dismiss();iop++;
                adapter.notifyDataSetChanged();
            }
        });
        //myDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog1.show();
    }

    public void showPopup(final int position){
        myDialog.setContentView(R.layout.popup_delete);

        Button del,can;
        //Toast.makeText(this.getContext(),productList.get(position).getLocation(),Toast.LENGTH_SHORT).show();
        del = (Button) myDialog.findViewById(R.id.delete);
        can = (Button) myDialog.findViewById(R.id.cancel);
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.remove(position);
                myDialog.dismiss();
                adapter.notifyDataSetChanged();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
    public void reload() {

        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(myDialog1.getContext());
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
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(myDialog1.getContext().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(myDialog1.getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    btn.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(myDialog1.getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            btn.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(myDialog1.getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
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
            MediaScannerConnection.scanFile(getContext(),
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

}
