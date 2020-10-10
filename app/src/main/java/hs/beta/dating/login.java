package hs.beta.dating;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class login extends Fragment {
    public login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TextView signUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    ImageButton back;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signUp=view.findViewById(R.id.signIn);
        final NavController navigationView = Navigation.findNavController(view);
        back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImageView views = (ImageView)v;
//                back.getBackground().setColorFilter(0x17000000, PorterDuff.Mode.SRC_ATOP);
//                back.invalidate();
                navigationView.popBackStack();
            }
        });
        signUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                long startClickTime =0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        startClickTime = (long) Calendar.getInstance().getTimeInMillis();
                        signUp.getBackground().setColorFilter(0x17000000, PorterDuff.Mode.SRC_ATOP);
                        signUp.invalidate();
                        return true;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    {
                        signUp.getBackground().clearColorFilter();
                        navigationView.navigate(R.id.action_login3_to_signIn2);
                        signUp.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
    }
}