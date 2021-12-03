package com.example.newuniproject2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable {
    // on below line we are creating variable
    // for our array list and swipe deck.
    private SwipeDeck cardStack;
    private ArrayList<UserFeatures> usersArrayList;
    private ArrayList<String> matches;
    private Button button8;
    private Button button9;
    private Button button10;
    private int drawable;
    private int counter;

    private FirebaseAuth mAuth;


    //button to go to login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        counter = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        checkUserSex();

        // on below line we are initializing our array list and swipe deck.
        usersArrayList = new ArrayList<>();
        matches = new ArrayList<>();
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
                    String[] nameArray = name.split(",");

                    String nameTrim = nameArray[1].substring(6,nameArray[1].length()-1);
                    String schoolTrim = nameArray[0].substring(8,nameArray[0].length());

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();







                    String nameCut = name.substring(6, name.length()-1);
                    if(!(child.getKey().equals(user.getUid()))){
                        if(counter == 0)
                            drawable = R.drawable.zid;
                        if(counter == 1)
                            drawable = R.drawable.hunter;
                        if(counter == 2)
                            drawable = R.drawable.mario;
                        else
                            drawable = R.drawable.profilepic;

                        usersArrayList.add(new UserFeatures(""+ nameTrim, "Senior"+counter, ""+schoolTrim, "3 Miles away", drawable ));
                        counter++;
                    }

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
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                for (DataSnapshot child : children) {


                    String name = child.getValue().toString();
                    String[] nameArray = name.split(",");
                    String nameTrim = nameArray[1].substring(6,nameArray[1].length()-1);
                    String schoolTrim = nameArray[0].substring(8,nameArray[0].length());
                    if(!(child.getKey().equals(user.getUid()))){
                        usersArrayList.add(new UserFeatures(""+ nameTrim, "Senior", ""+schoolTrim, "2 Miles away", R.drawable.peach2 ));
                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // on below line we are adding data to our array list.
        usersArrayList.add(new UserFeatures("Luigi", "Senior", "LSU", "5 Miles away", R.drawable.luigi));

        // on below line we are creating a variable for our adapter class and passing array list to it.
        final DeckAdapter adapter = new DeckAdapter(usersArrayList, this);

        // on below line we are setting adapter to our card stack.
        cardStack.setAdapter(adapter);
        int count = 0;


        // on below line we are setting event callback to our card stack.
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {

            String matchName = "";
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.
                //Toast.makeText(MainActivity.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                // on card swiped to right we are displaying a toast message.
                matches.add(usersArrayList.get(position).getUsersName());




                //Toast.makeText(MainActivity.this, "Card Swiped Right", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                Toast.makeText(MainActivity.this, "No more potential matches left", Toast.LENGTH_SHORT).show();
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

        //button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMatchesActivity();
            }
        });
        //button10 = findViewById(R.id.button10);


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
    public void openMatchesActivity(){
        Intent intent = new Intent(this, matchesActivity.class);
        intent.putExtra("MainActivity", matches);
        intent.putStringArrayListExtra("test", (ArrayList<String>) matches);


        startActivity(intent);

        //finish();
        //return;
    }

    public void chatActivity(View view){
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, chatActivity.class);
        startActivity(intent);
        finish();
        return;
    }


}
