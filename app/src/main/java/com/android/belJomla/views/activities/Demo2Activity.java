package com.android.belJomla.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.belJomla.R;
import com.android.belJomla.utils.Constants ;
import com.android.belJomla.utils.LoggerUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Demo2Activity extends AppCompatActivity {

    public FirebaseAuth authInstance = FirebaseAuth.getInstance();
    public FirebaseFirestore firestoreIstance = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);


        Toast.makeText(this,"xxx",Toast.LENGTH_SHORT).show();
        firestoreIstance.collection(Constants.DEMO_USER_PATH).add(new DemoUser("Abdullah", "Barnawi", "asdasd")).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                LoggerUtils.Companion.logMessage(this, "User Posted ! ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                LoggerUtils.Companion.logMessage(this, "User Not Posted ! ");

            }
        });


    }


    class DemoUser {

        String name = "";
        String id = "";
        String mobile = "";

        public DemoUser(String name, String id, String mobile) {
            this.name = name;
            this.id = id;
            this.mobile = mobile;
        }
    }
}


