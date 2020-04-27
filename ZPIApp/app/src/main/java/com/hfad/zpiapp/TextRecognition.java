package com.hfad.zpiapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.biometrics.BiometricPrompt;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.List;

import androidx.annotation.NonNull;

public class TextRecognition {
    private String textFromImage="";

    public void detectTextFromImage(final Context ctx, Bitmap photo)
    {
        FirebaseVisionImage visionImage = FirebaseVisionImage.fromBitmap(photo);
        FirebaseVisionTextRecognizer textRecognizer =
                FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        Task<FirebaseVisionText> task = textRecognizer.processImage(visionImage);
        task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                textFromImage = takeTextFromImage(ctx, firebaseVisionText);
                Log.println(Log.ASSERT, "tu byłem" , textFromImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ctx, "Error", Toast.LENGTH_LONG);
            }
        });
        try {
            Tasks.await(task);
            Log.println(Log.ASSERT,"cokolwiekAgain", textFromImage);
        }
        catch(Exception e)
        {
            Toast.makeText(ctx, "Ups!", Toast.LENGTH_LONG);
        }
    }

    public String getTextFromImage()
    {
        Log.println(Log.ASSERT,"cokolwiek", textFromImage);
        return textFromImage;
    }

    private String takeTextFromImage(Context ctx, FirebaseVisionText textOnImage) {
        List<FirebaseVisionText.TextBlock> blocks = textOnImage.getTextBlocks();
        if(blocks.size() == 0)
        {
            Toast.makeText(ctx, "No text on image", Toast.LENGTH_LONG);

        }
        else
        {
            String textOnImg="";
            for(FirebaseVisionText.TextBlock block : blocks)
            {
                textOnImg += block.getText();

                //return textOnImg;
            }
            Log.println(Log.ASSERT, "tu byłem" , textOnImg);
            return textOnImg;
        }
        return "";
    }

}
