package com.hfad.zpiapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import androidx.annotation.NonNull;

public class TextRecognition implements Runnable {
    private String textFromImage;
    Bitmap photo;
    Context ctx;
     CountDownLatch cdl;

    public TextRecognition(final Context ctx, Bitmap photo,  CountDownLatch cdl)
    {
      this.ctx=ctx;
      this.photo=photo;
      this.cdl=cdl;
    }

    public String getTextFromImage()
    {
       // Log.println(Log.ASSERT,"cokolwiek", textFromImage);
        return textFromImage.toString();
    }

    private String takeTextFromImage(Context ctx, FirebaseVisionText textOnImage) {
        List<FirebaseVisionText.TextBlock> blocks = textOnImage.getTextBlocks();
        if(blocks.size() == 0)
        {
            Toast.makeText(ctx, "No text on image", Toast.LENGTH_LONG);

        }
        else
        {
            StringBuilder textOnImg=new StringBuilder("");
            for(FirebaseVisionText.TextBlock block : blocks)
            {
                textOnImg.append(block.getText());
            }
            return textOnImg.toString();
        }
        return "";
    }

    @Override
    public void run() {
        Looper.prepare();
        FirebaseVisionImage visionImage = FirebaseVisionImage.fromBitmap(photo);
        FirebaseVisionTextRecognizer textRecognizer =
                FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        Task<FirebaseVisionText> task = textRecognizer.processImage(visionImage);
        task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                try {
                    textFromImage=takeTextFromImage(ctx,firebaseVisionText);
                }finally {
                    Toast.makeText(ctx,textFromImage,Toast.LENGTH_LONG).show();
                    cdl.countDown();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ctx, "Error", Toast.LENGTH_LONG);
                cdl.countDown();
            }
        });
    }
}
