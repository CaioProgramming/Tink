package com.inlustris.tink.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inlustris.tink.Beans.Riddle;
import com.inlustris.tink.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private Activity activity;
    private ArrayList<Riddle> riddles;

    public ViewPagerAdapter(Activity activity, ArrayList<Riddle> riddles) {
        this.activity = activity;
        this.riddles = riddles;
    }

    String messages[] = {
      "É... Não, tenta de novo, eu acredito em você.",
      "Hum... Talvez esteja certo, só que não para essa pergunta...",
      "Afinal o que é certo e o que é errado né? A única coisa que sei é que sua resposta está errada."
    };

    @Override
    public int getCount() {

        if ( riddles == null  || riddles.size() == 0) {
            return 0;
        } else {
            return riddles.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final Riddle riddle = riddles.get(position);
          LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View view = inflater.inflate(R.layout.riddle_layout, container, false);
        final Button seeanswer = (Button) view.findViewById(R.id.seeanswer);LinearLayout ridle = (LinearLayout) view.findViewById(R.id.ridle);
       final TextView answer = view.findViewById(R.id.answer);
       TextView riddletxt = view.findViewById(R.id.riddle);
       riddletxt.setText(riddle.getRiddle());
       answer.setText(riddle.getAnswer());
        Typeface font = Typeface.createFromAsset(activity.getAssets(),"fonts/Baloo-Regular.ttf");
        riddletxt.setTypeface(font);
         answer.setTypeface(font);

       seeanswer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (answer.getVisibility() == View.VISIBLE){
                   answer.setVisibility(View.GONE);
               }else{
                   answer.setVisibility(View.VISIBLE);
               }
           }
       });
       container.addView(view);
       return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
