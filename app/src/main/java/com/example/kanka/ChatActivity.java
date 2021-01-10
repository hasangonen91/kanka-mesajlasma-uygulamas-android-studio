package com.example.kanka;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    String userName,otherName;
    TextView chatUserName;
    ImageView backImage,sendImage;
    EditText chatEditTex;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView chatRecyView;
    MesajAdapter mesajAdapter;
    List<MesajModel>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tanimla();
        loadMesaj();

    }
    public void tanimla(){
        list=new ArrayList<>();
        userName = getIntent().getExtras().getString("username");
        otherName =getIntent().getExtras().getString("othername");

        Log.i("alınandegerler",userName+"<-->"+otherName);
        chatUserName=(TextView)findViewById(R.id.chatUserName);
        backImage=(ImageView)findViewById(R.id.backImage);
        sendImage=(ImageView) findViewById(R.id.sendImage);
        chatEditTex=(EditText)findViewById(R.id.chatEditTex);
        chatUserName.setText(otherName);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this,MainActivity.class);
                intent.putExtra("kadi",userName);
                startActivity(intent);

            }
        });

        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();
        sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesaj=chatEditTex.getText().toString();
                chatEditTex.setText("");
                mesajGonder(mesaj);
            }
        });
        chatRecyView=(RecyclerView)findViewById(R.id.chatRecyView);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(ChatActivity.this,1);
        chatRecyView.setLayoutManager(layoutManager);
        mesajAdapter=new MesajAdapter(ChatActivity.this,list,ChatActivity.this,userName);
        chatRecyView.setAdapter(mesajAdapter);
    }
    public void mesajGonder(String text){
        String key=reference.child("Mesajlar").child(userName).child(otherName).push().getKey();
        final Map<String, String> object = new HashMap<>();
        object.put("mesaj", text);
        object.put("gonderen", userName);
        list.add(new MesajModel(userName, text));
        mesajAdapter.notifyDataSetChanged();
        chatRecyView.scrollToPosition(list.size()-1);
        reference.child(userName).child(otherName).child(key).setValue(object).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    reference.child("Mesajlar").child(otherName).child(userName).setValue(object).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }
            }
        });
    }

    public void loadMesaj(){

        reference.child("Mesajlar").child(userName).child(otherName).child("mesaj").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.e(otherName, (String) dataSnapshot.getValue());
                if(dataSnapshot.exists()) {
                    MesajModel mesajModel = null;
                    mesajModel = new MesajModel(otherName, (String) dataSnapshot.getValue());
                    list.add(mesajModel);
                    mesajAdapter.notifyDataSetChanged();
                    chatRecyView.scrollToPosition(list.size() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
