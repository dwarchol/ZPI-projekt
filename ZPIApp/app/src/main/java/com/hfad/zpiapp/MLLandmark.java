package com.hfad.zpiapp;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
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

        System.out.println("Liczba cdl "+cdl.getCount());

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

                      cdl.countDown();

                    }
                });

        System.out.println("at the end "+cdl.getCount());

    }

}
