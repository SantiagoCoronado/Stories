package com.example.santiagocoronado.storiespv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.santiagocoronado.storiespv.Modelos.OnSwipeTouchListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class MainActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener{

    StoriesProgressView storiesProgressView;
    ImageView imageView, iv_back;


    int counter = 0;
    /*int[] resources = new int[]{
            R.drawable.smash0,
            R.drawable.smash5,
            R.drawable.smash1,
            R.drawable.smash2
    };*/

    ArrayList<String> imagenes;
    ArrayList<Long> durations;
    Long[] durArray;
    long[] chido;

    String[] resources;


    Long cStart, cStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagenes = new ArrayList<>();
        imagenes.add("https://scontent.fmex7-1.fna.fbcdn.net/v/t1.0-9/43291464_1947932041909549_7615597780316192768_o.jpg?_nc_cat=100&oh=c1db23bc48092f9c4fee9c51a9c92735&oe=5C474117");
        imagenes.add("https://scontent.fmex7-1.fna.fbcdn.net/v/t1.0-9/43135843_1947936055242481_8233990324100268032_n.jpg?_nc_cat=105&oh=0ef3941eb53cdce62a3d1cd68c057deb&oe=5C5E4947");

        resources = new String[imagenes.size()];
        resources = imagenes.toArray(resources);

        durations = new ArrayList<>();
        durations.add(4000L);
        durations.add(10000L);

        durArray = new Long[durations.size()];
        durArray = durations.toArray(durArray);

        chido =  ArrayUtils.toPrimitive(durArray);

        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        storiesProgressView.setStoriesCount(resources.length);
        storiesProgressView.setStoriesCountWithDurations(chido);
        storiesProgressView.setStoriesListener(this);
        //storiesProgressView.startStories();
        iv_back = findViewById(R.id.iv_back);
        imageView = findViewById(R.id.image);
        iv_back.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    storiesProgressView.pause();
                    cStart = System.currentTimeMillis();
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    cStop = System.currentTimeMillis();
                    Log.i("TIMER_START", String.valueOf(cStart));
                    Log.i("TIMER_STOP", String.valueOf(cStop));
                    //Log.i("TIMER_FLAG", String.valueOf(flag));
                    if(cStop < cStart+100L ){
                        storiesProgressView.skip();
                        //chrono.setBase(0L);
                    }else {
                        storiesProgressView.resume();
                        //chrono.setBase(0L);
                    }
                }
                return true;
            }
        });
        iv_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    storiesProgressView.pause();
                    cStart = System.currentTimeMillis();
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    cStop = System.currentTimeMillis();
                    Log.i("TIMER_START", String.valueOf(cStart));
                    Log.i("TIMER_STOP", String.valueOf(cStop));
                    //Log.i("TIMER_FLAG", String.valueOf(flag));
                    if(cStop < cStart+120L ){
                        storiesProgressView.reverse();
                        //chrono.setBase(0L);
                    }else {
                        storiesProgressView.resume();
                        //chrono.setBase(0L);
                    }
                }
                return true;
            }
        });




        //imageView.setImageResource(imagenes.get(counter));
        //storiesProgressView.startStories();
        //Glide.with(MainActivity.this).load(imagenes.get(counter)).into(imageView);
        Picasso.get().load(resources[counter]).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                storiesProgressView.startStories();
                imageView.setVisibility(View.VISIBLE);
                iv_back.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });


    }

    @Override
    public void onNext() {
        //imageView.setImageResource(resources[++counter]);
        if (counter<imagenes.size()-1) {

            storiesProgressView.pause();
            Picasso.get().load(resources[++counter]).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    storiesProgressView.resume();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
    }

    @Override
    public void onPrev() {
        if(counter > 0) {
            storiesProgressView.pause();
            //imageView.setImageResource(resources[--counter]);

            Picasso.get().load(resources[--counter]).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    storiesProgressView.resume();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
    }

    @Override
    public void onComplete() {
        //Toast.makeText(MainActivity.this, "Completado", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        storiesProgressView.destroy();
        super.onDestroy();
    }
}
