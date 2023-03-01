package com.example.cloudapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button btnSave;
    EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSave = findViewById(R.id.btn_save);
        edtName = findViewById(R.id.et_user_name);
    }

    public void saveToFirebase(View v) {
        String userName = edtName.getText().toString();
        if (!userName.isEmpty()) {
            Map<String, Object> user = new HashMap<>();
            user.put("name", userName);

// Add a new document with a generated ID
            Task<DocumentReference> documentReferenceTask = db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            OpenSecondActivity();
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error adding document", e);
                        }
                    });
        } else {
            Toast.makeText(this,"Please Empty the Empty Field",Toast.LENGTH_LONG).show();
        }
    }
    public void OpenSecondActivity(){
        Intent intent= new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
}
