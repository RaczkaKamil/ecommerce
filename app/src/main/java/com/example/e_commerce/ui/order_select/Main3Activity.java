package com.example.e_commerce.ui.order_select;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.model.OrderDao;
import com.example.e_commerce.model.RealmManager;
import com.example.e_commerce.ui.order_list.HomeListAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmResults;

public class Main3Activity extends AppCompatActivity {
    int id = 0;
    OrderDao dao;
    Order order;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RESULT_LOAD_IMAGE = 2;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;

    Button button_take;
    Button button_add;
    TextView tf_title;
    TextView tf_name;
    TextView tf_date;
    TextView tf_photo;

    RecyclerView recyclerView;
    OrderListAdapter adapter;
    ArrayList<String> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id = getIntent().getIntExtra("ID", 0);
        setView();
        refreshList();
    }





    public void setView(){
         recyclerView = findViewById(R.id.rv);
         button_take = findViewById(R.id.button_take);
         button_add = findViewById(R.id.button_add);
         tf_title = findViewById(R.id.tf_title);
         tf_name = findViewById(R.id.tf_name);
         tf_date = findViewById(R.id.tf_date);
         tf_photo = findViewById(R.id.tf_photo);

        button_take.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });

        button_add.setOnClickListener(v -> {
            if (!checkIfAlreadyhavePermission()) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                dispatchSelectPictureIntent();
            }

        });

        adapter = new OrderListAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    dispatchSelectPictureIntent();
                } else {
                    Toast.makeText(this, "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void dispatchSelectPictureIntent(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public void refreshList(){
        refreshOrder();
        tf_title.setText(order.getTitle());
        tf_name.setText(order.getName());
        tf_date.setText(order.getDate());
        tf_photo.setText(order.getPhotoCount());
    }

    public void refreshOrder(){
        dao = RealmManager.createOrderDao();
        order =  dao.getOrderByID(id);
        try{
            refreshOrderList();
        }catch (NullPointerException e){

        }

    }

    public void refreshOrderList(){
        list.clear();
        String path = order.getPhoto();
        String[] paths = path.split(",");
        for (String p : paths) {
            if ((p.length() > 5)) {
                list.add(p);
            }
        }
        adapter.notifyDataSetChanged();
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    String  currentPhotoPath;
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

         currentPhotoPath = image.getAbsolutePath();
        return image;

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
           saveImage(currentPhotoPath);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedImage != null) {
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                    saveImage(picturePath);
                    cursor.close();

            }
        }
    }

    private void saveImage(String path){
        RealmManager.open();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        dao.addNewPhotoToOrder(currentDateandTime+"*"+path, id);
        refreshList();

        ConstraintLayout constaint = findViewById(R.id.constaint);
        Snackbar snackbar = Snackbar
                .make(constaint, "Zdjęcie zostało dodane do zamówienia.", Snackbar.LENGTH_LONG);
        snackbar.show();
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}