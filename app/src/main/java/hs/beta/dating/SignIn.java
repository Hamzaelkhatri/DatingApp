package hs.beta.dating;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.hbb20.CountryCodePicker;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.Calendar;


public class SignIn extends Fragment {


    public SignIn() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    CountryCodePicker countryCodeHolder;
    TextView login;
    TextView conditionLink;
    MaterialButton register;
    ImageView back;
    MaterialEditText Phone;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navigationView = Navigation.findNavController(view);
        countryCodeHolder=view.findViewById(R.id.countryCodeHolder);
        login=view.findViewById(R.id.login);
        back=view.findViewById(R.id.back);
        conditionLink=view.findViewById(R.id.conditionLink);
        register = view.findViewById(R.id.register);
        Phone=view.findViewById(R.id.Phone);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Phone.getText().toString().isEmpty())
                {
                    Phone.findFocus();
                    Phone.requestFocus();
                    Phone.setError("Please set your phone number");
                }
                else if(Phone.getText().toString().length()<9)
                {
                    Phone.setError("Please set a exists phone number");
                }
                else if(!Phone.getText().toString().trim().matches("^[0-9]*$"))
                    Phone.setError("Please set a exists phone format ex: 04733434343");
                else
                {
                    int phone = Integer.parseInt(Phone.getText().toString());
                    SignInDirections.ActionSignIn2ToOptMessage action = SignInDirections.actionSignIn2ToOptMessage();
                    action.setPhone("+"+countryCodeHolder.getSelectedCountryCode()+phone);
                    navigationView.navigate(action);
                }
            }
        });

        conditionLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://hello.com"));
                startActivity(i);
            }
        });

//        conditionLink.setText(Html.fromHtml(getString(R.string.i_accept_the_terms_and_conditions)));
//        conditionLink.setMovementMethod(LinkMovementMethod.getInstance());

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                navigationView.navigate(R.id.action_signIn2_to_opt_message);
//            }
//        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImageView views = (ImageView)v;
//                back.getBackground().setColorFilter(0x17000000, PorterDuff.Mode.SRC_ATOP);
//                back.invalidate();
                navigationView.popBackStack();
            }
        });
        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                long startClickTime =0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        startClickTime = (long) Calendar.getInstance().getTimeInMillis();
                        login.getBackground().setColorFilter(0x17000000, PorterDuff.Mode.SRC_ATOP);
                        login.invalidate();
                        return true;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    {
                        login.getBackground().clearColorFilter();
                        navigationView.navigate(R.id.action_signIn2_to_login3);
                        login.invalidate();
                        long clickDuration = (long) (Calendar.getInstance().getTimeInMillis() - startClickTime);
                        break;
                    }
                }
                return true;
            }
        });


    }
}