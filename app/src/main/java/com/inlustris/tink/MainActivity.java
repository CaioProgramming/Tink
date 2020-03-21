package com.inlustris.tink;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.inlustris.tink.Adapter.ViewPagerAdapter;
import com.inlustris.tink.Beans.Riddle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser user;
    private Query riddledb;
    private ArrayList<Riddle> riddles;
    private android.support.v4.view.ViewPager viewpager;
    private android.support.design.widget.TabLayout tabs;
    private android.widget.ProgressBar progress;
    private android.widget.TextView message;
    private android.widget.LinearLayout loading;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.loading = findViewById(R.id.loading);
        this.message = findViewById(R.id.message);
        this.progress = findViewById(R.id.progress);
        this.tabs = findViewById(R.id.tabs);
        this.viewpager = findViewById(R.id.viewpager);
        riddledb = FirebaseDatabase.getInstance().getReference();
        riddles = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null){
            Carregar();
        }else{
            List<AuthUI.IdpConfig> providers = Collections.singletonList(
                    new AuthUI.IdpConfig.GoogleBuilder().build());
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                    .setLogo(R.mipmap.ic_launcher)
                    .setAvailableProviders(providers)
                    .setTheme(R.style.AppTheme)
                    .build(),RC_SIGN_IN);
        }
        tabs.setupWithViewPager(viewpager,true);


    }
    private void Carregar(){
        riddledb = FirebaseDatabase.getInstance().getReference("riddles");
        riddledb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                riddles.clear();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    Riddle riddle = new Riddle();
                    Riddle r = d.getValue(Riddle.class);
                    if (r != null){
                        riddle.setAnswer(r.getAnswer());
                        riddle.setRiddle(r.getRiddle());
                        riddles.add(riddle);

                        System.out.println(riddle.getRiddle());
                    }
                }
                Collections.reverse(riddles);
                Slidesetup(riddles);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("erro no banco de dados " + databaseError.getMessage());
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK){
                Carregar();



            }else{
                if (response != null) {
                    Toast.makeText(this, "Erro " + Objects.requireNonNull(response.getError()).getMessage() + " causa " + response.getError().getCause(), Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    private void setupTabIcons(int size) {

        for (int i = 0; i  < size; i++){
            System.out.println("Charadas " + i);
            tabs.addTab(tabs.newTab().setIcon(this.getDrawable(R.drawable.dot)));

        }

    }


    private void Slidesetup(ArrayList<Riddle> riddlelist) {
        if (riddlelist != null && riddlelist.size() > 0){
            ViewPagerAdapter adapter = new ViewPagerAdapter(this,riddles);
            loading.setVisibility(View.GONE);
            setupTabIcons(riddlelist.size());
            viewpager.setAdapter(adapter);
            viewpager.setVisibility(View.VISIBLE);
        }
            else{
            progress.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            message.setText("A verdadeira pergunta aqui é... Onde foram parar as charadas?");

        }
    }
}
