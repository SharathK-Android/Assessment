package test.com.assessment.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import test.com.assessment.Class.DataModel;

public class DetailsActivity extends AppCompatActivity {

    private DataModel dataModel;
    TextView txtName,txtCity,txtSalary,txtDesignation,txtDate;
    Button btnCamera;
    private static final int CAMERA_REQUEST = 01;
    private static final int MY_CAMERA_PERMISSION_CODE = 02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        dataModel= getIntent().getParcelableExtra("model");

        txtName=(TextView)findViewById(R.id.txt_name);
        txtCity=(TextView)findViewById(R.id.text_city);
        txtSalary=(TextView)findViewById(R.id.text_salary);
        txtDesignation=(TextView)findViewById(R.id.text_designation);
        txtDate=(TextView)findViewById(R.id.text_date);
        btnCamera=(Button)findViewById(R.id.btn_camera);

        txtName.setText(dataModel.getName());
        txtCity.setText(dataModel.getCity());
        txtSalary.setText(dataModel.getSalary());
        txtDesignation.setText(dataModel.getDesignation());
        txtDate.setText(dataModel.getDate());

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkForPermission();
            }
        });

    }

    private void checkForPermission() {

        if (ContextCompat.checkSelfPermission(DetailsActivity.this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetailsActivity.this,new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_PERMISSION_CODE);
        } else {
            openCamera();

        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            showImage(photo);

        }
    }

    private void showImage(Bitmap photo) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent intent = new Intent(this, PhotoResultActivity.class);
        intent.putExtra("image",byteArray);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
