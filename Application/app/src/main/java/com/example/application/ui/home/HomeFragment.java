package com.example.application.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.application.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView imageView = root.findViewById(R.id.image_view1);
        generateCode(imageView);

        return root;
    }


    public  void generateCode (ImageView imageView )
    {

        ///Bitmap bitmap = null;
        //Authentication authentication = new Authentication();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();




        if ( firebaseUser != null)
        {
            String email = firebaseUser.getEmail();
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try
            {
                BitMatrix bitMatrix = multiFormatWriter.encode(email, BarcodeFormat.QR_CODE, 500, 500);
                // Use ZXing Android Embedded code to write
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                //ImageView imageView = findViewById(R.id.image_view);
                imageView.setImageBitmap(bitmap);

            }
            catch (WriterException e)
            {
                e.printStackTrace();
            }
            catch (IllegalArgumentException iae)
            {

            }
        }



    }

}
