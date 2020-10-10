package hs.beta.dating;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import hs.beta.dating.adapter.Slide_adapter;
import hs.beta.dating.models.Slide;
import hs.beta.dating.views.CustomPager;

public class first_home extends Fragment implements View.OnTouchListener {

    CustomPager pager;
    Slide_adapter slideAdapter;
    TabLayout indicator;
    List<Slide> slides;
    AppCompatButton getStarted;
    TextView login;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_first_home, container, false);;
//        pager =view.findViewById(R.id.pager);
//        indicator=view.findViewById(R.id.indicator);
//        getStarted =view.findViewById(R.id.getStarted);
//        login = view.findViewById(R.id.login);
//        final NavController navigationView = Navigation.findNavController(view);
//        getStarted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                navigationView.navigate(R.id.action_first_home3_to_login3);
//                Toast.makeText(getContext(),"HERE",Toast.LENGTH_LONG).show();
//            }
//        });
//        getStarted.setOnTouchListener(this);
//        login.setOnTouchListener(this);
//        slides= new ArrayList<>();
//        slides.add(new Slide(R.drawable.pager1,"Discover",getString(R.string.large_string)));
//        slides.add(new Slide(R.drawable.pager2,"Discover",getString(R.string.large_string)));
//        slides.add(new Slide(R.drawable.pager3,"Discover",getString(R.string.large_string)));
//        slides.add(new Slide(R.drawable.pager4,"Discover",getString(R.string.large_string)));
//        slideAdapter = new Slide_adapter(slides,getActivity());
//        pager.setAdapter(slideAdapter);
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new pagerTimer(),2000,4000);
//        indicator.setupWithViewPager(pager,true);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager =view.findViewById(R.id.pager);
        indicator=view.findViewById(R.id.indicator);
        getStarted =view.findViewById(R.id.getStarted);
        login = view.findViewById(R.id.login);
        final NavController navigationView = Navigation.findNavController(view);

        getStarted.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float startClickTime =0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    {
                        startClickTime = Calendar.getInstance().getTimeInMillis();
//                        TextView view = (TextView) v;
                        v.getBackground().setColorFilter(0x17000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        long clickDuration = (long) (Calendar.getInstance().getTimeInMillis() - startClickTime);
                        if(clickDuration>100)
                        {
                            navigationView.navigate(R.id.action_first_home3_to_signIn2);
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    {
                        View view = v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                long startClickTime =1;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    {
                        startClickTime = (long)Calendar.getInstance().getTimeInMillis();
                        TextView view = (TextView) v;
                        view.getBackground().setColorFilter(0x17000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        long clickDuration = (long) (Calendar.getInstance().getTimeInMillis() - startClickTime);
                        if(clickDuration > 100)
                        {
                            navigationView.navigate(R.id.action_first_home3_to_login3);
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    {
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
        slides= new ArrayList<>();
        slides.add(new Slide(R.drawable.pager1,"Discover",getString(R.string.large_string)));
        slides.add(new Slide(R.drawable.pager2,"Discover",getString(R.string.large_string)));
        slides.add(new Slide(R.drawable.pager3,"Discover",getString(R.string.large_string)));
        slides.add(new Slide(R.drawable.pager4,"Discover",getString(R.string.large_string)));
        slideAdapter = new Slide_adapter(slides,getActivity());
        pager.setAdapter(slideAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new pagerTimer(),2000,6000);
        indicator.setupWithViewPager(pager,false);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return true;
    }

    class pagerTimer extends TimerTask
    {
        @Override
        public void run()
        {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        if (pager.getCurrentItem()<3)
                            pager.setCurrentItem(pager.getCurrentItem()+1);
                        else
                            pager.setCurrentItem(0);
                    }
                });
            }
            catch (Exception ex)
            {

            }
        }
    }
}