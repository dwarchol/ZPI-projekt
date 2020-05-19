package com.hfad.zpiapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark;
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionLatLng;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import androidx.annotation.NonNull;

public class MLLandmark implements Runnable {
    FirebaseVisionImage image;
    StringBuilder sb = new StringBuilder();
    Context ctx;
    FirebaseVisionImageLabeler detector ;
    CountDownLatch cdl;
    int index;
    public MLLandmark(Bitmap photo,Context ctx, CountDownLatch cdl)
    {
        this.cdl=cdl;

        image = FirebaseVisionImage.fromBitmap(photo);
        detector= FirebaseVision.getInstance()
                .getOnDeviceImageLabeler();
        this.ctx=ctx;
    }



    @Override
    public void run() {
       // Looper.prepare();
        System.out.println("Liczba cdl "+cdl.getCount());
        //final CountDownLatch cdl2 = new CountDownLatch(1);
        //Toast.makeText(ctx, "please work", Toast.LENGTH_LONG).show();
        detector.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                        try {
                            for (FirebaseVisionImageLabel label : labels) {
                                String text = label.getText();
                                String entityId = label.getEntityId();
                                float confidence = label.getConfidence();

                                sb.append(text + " ");
                            }
                            //System.out.println(index);
                            //System.out.println("Something fucked up");
                           // System.out.println(cdl.getCount());

                            //System.out.println(sb.toString());
                            // Toast.makeText(ctx, sb, Toast.LENGTH_LONG).show();

                            // cdl2.countDown();

                        }finally {
                            cdl.countDown();
                            System.out.println("on success " + cdl.getCount());
                        }
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        sb.append("failure");
                        //Toast.makeText(ctx,sb,Toast.LENGTH_LONG).show();
                      cdl.countDown();
                      //cdl2.countDown();
                    }
                });

        System.out.println("at the end "+cdl.getCount());
       // sb.append("Skyscraper ");
    }

}
