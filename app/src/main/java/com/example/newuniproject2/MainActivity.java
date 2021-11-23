package com.example.newuniproject2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.newuniproject2.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // on below line we are creating variable
    // for our array list and swipe deck.
    private SwipeDeck cardStack;
    private ArrayList<UserFeatures> usersArrayList;

    private FirebaseAuth mAuth;

    //button to go to login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        checkUserSex();

        // on below line we are initializing our array list and swipe deck.
        usersArrayList = new ArrayList<>();
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        // go through each database member
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // gets top level of database
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("Users").child("Male").addValueEventListener(new ValueEventListener() {
            // invoked any time data from the database changes
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // gets all children
                Iterable<DataSnapshot> children = snapshot.getChildren();

                for (DataSnapshot child : children) {
                    String name = child.getValue().toString();
                    usersArrayList.add(new UserFeatures(""+ name, "Senior", "LSU", "2 Miles away", R.drawable.gfg ));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("Users").child("Female").addValueEventListener(new ValueEventListener() {
            // invoked any time data from the database changes
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // gets all children
                Iterable<DataSnapshot> children = snapshot.getChildren();

                for (DataSnapshot child : children) {
                    String name = child.getValue().toString();
                    usersArrayList.add(new UserFeatures(""+ name, "Senior", "LSU", "2 Miles away", R.drawable.gfg ));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // on below line we are adding data to our array list.
        usersArrayList.add(new UserFeatures("Student 1", "Senior", "LSU", "2 Miles away", R.drawable.gfg));
        usersArrayList.add(new UserFeatures("Student 2", "Freshman", "LSU", "18 Miles away", R.drawable.gfg));
        usersArrayList.add(new UserFeatures("Student 3", "Freshman", "LSU", "4 Miles away", R.drawable.gfg));
        usersArrayList.add(new UserFeatures("Student 4", "Sophomore", "LSU", "5 Miles away", R.drawable.gfg));
        usersArrayList.add(new UserFeatures("Student 5", "Junior", "LSU", "10 Miles away", R.drawable.gfg));

        // on below line we are creating a variable for our adapter class and passing array list to it.
        final DeckAdapter adapter = new DeckAdapter(usersArrayList, this);

        // on below line we are setting adapter to our card stack.
        cardStack.setAdapter(adapter);

        // on below line we are setting event callback to our card stack.
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.
                Toast.makeText(MainActivity.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                // on card swiped to right we are displaying a toast message.
                Toast.makeText(MainActivity.this, "Card Swiped Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                Toast.makeText(MainActivity.this, "No more users in your area", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardActionDown() {
                // this method is called when card is swiped down.
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
                // this method is called when card is moved up.
                Log.i("TAG", "CARDS MOVED UP");
            }

        });


    }
    private String userSex;
    private String oppositeSex;
    public void checkUserSex(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference maleDb = FirebaseDatabase.getInstance().getReference().child("Users").child("Male");
        maleDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().equals(user.getUid())){
                    userSex = "Male";
                    oppositeSex = "Female";
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference femaleDb = FirebaseDatabase.getInstance().getReference().child("Users").child("Female");
        maleDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().equals(user.getUid())){
                    userSex = "Female";
                    oppositeSex = "Male";
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void getOppositeSexUsers(){

    }
    // control for logging out button
    public void userLogout(View view){
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginOrRegistrationActivity.class);
        startActivity(intent);
        finish();
        return;
    }

}
