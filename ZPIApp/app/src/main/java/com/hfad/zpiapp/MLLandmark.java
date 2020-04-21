package com.hfad.zpiapp;

import android.content.Context;
import android.graphics.Bitmap;
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

import androidx.annotation.NonNull;

public class MLLandmark {
    FirebaseVisionImage image;
    StringBuilder sb = new StringBuilder();
    Context ctx;
    FirebaseVisionImageLabeler detector ;
    public MLLandmark(Bitmap photo,Context ctx)
    {
        image = FirebaseVisionImage.fromBitmap(photo);
        detector= FirebaseVision.getInstance()
                .getOnDeviceImageLabeler();
        this.ctx=ctx;
    }

   public String labeler (final int i)
   {

       detector.processImage(image)
               .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                   @Override
                   public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                       for (FirebaseVisionImageLabel label: labels) {
                           String text = label.getText();
                           String entityId = label.getEntityId();
                           float confidence = label.getConfidence();

                           sb.append(text+" ");
                       }
                       System.out.println(i);
                       System.out.println(sb.toString());
                       Toast.makeText(ctx,sb,Toast.LENGTH_LONG).show();
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                     sb.append("failure");
                       Toast.makeText(ctx,sb,Toast.LENGTH_LONG).show();
                   }
               });
       return sb.toString();
   };

}