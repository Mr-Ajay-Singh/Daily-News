package com.cyris.dailynews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ScreenShot {

    public static Bitmap takeScreenShot(View view)
    {

        Bitmap bitmap =Bitmap.createBitmap(view.getMeasuredWidth(),view.getMeasuredHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    public static void ShareBitmap(View view, Context context) {
        Bitmap bitmap = takeScreenShot(view);

      /*  File f3=new File(Environment.getExternalStorageDirectory()+"/DailyNews/");
        if(!f3.exists())
            f3.mkdirs();
        File file = new File(f3+String.valueOf(System.currentTimeMillis())+".jpg");*/

        String path2 = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
       // String path2 = file.getPath();
        Uri uri = Uri.parse(path2);


        ByteArrayOutputStream stream = null;

        try {
            stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

            stream.flush();
            ShareData(context,uri);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void ShareData(Context context, Uri path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM,path);
        context.startActivity(Intent.createChooser(intent,"ShareData"));

    }


}
