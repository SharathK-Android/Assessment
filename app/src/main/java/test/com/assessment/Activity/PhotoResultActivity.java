package test.com.assessment.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class PhotoResultActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_result);
        imageView=(ImageView)findViewById(R.id.imageview);

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(Calendar.getInstance().getTime());

        Bitmap bitmap=bmp.copy(Bitmap.Config.ARGB_8888, true);

        Canvas cs = new Canvas(bitmap);
        Paint tPaint = new Paint();
        tPaint.setTextSize(10);
        tPaint.setColor(Color.WHITE);
        tPaint.setStyle(Paint.Style.FILL);
        cs.drawBitmap(bmp, 0f, 0f, null);
        float height = tPaint.measureText("yY");
        cs.drawText(dateTime, 5f, height+5f, tPaint);

        imageView.setImageBitmap(bitmap);
        bmp.recycle();
        bmp=null;bitmap=null;
    }
}
