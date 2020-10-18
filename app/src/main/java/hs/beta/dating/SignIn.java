package hs.beta.dating;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.telephony.TelephonyManager;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareOpenGraphValueContainer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;
import com.rengwuxian.materialedittext.MaterialEditText;
import org.json.JSONObject;
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
    MaterialButton register,fb;
    ImageView back;
    MaterialEditText Phone;
    LoginButton facebook;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navigationView = Navigation.findNavController(view);
        countryCodeHolder = view.findViewById(R.id.countryCodeHolder);
        login = view.findViewById(R.id.login);
        back = view.findViewById(R.id.back);
        conditionLink = view.findViewById(R.id.conditionLink);
        register = view.findViewById(R.id.register);
        Phone = view.findViewById(R.id.Phone);
        facebook = new LoginButton(getContext());
        fb= view.findViewById(R.id.facebook);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebook.performClick();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Phone.getText().toString().isEmpty()) {
                    Phone.findFocus();
                    Phone.requestFocus();
                    Phone.setError("Please set your phone number");
                } else if (Phone.getText().toString().length() < 9) {
                    Phone.setError("Please set a exists phone number");
                } else if (!Phone.getText().toString().trim().matches("^[0-9]*$"))
                    Phone.setError("Please set a exists phone format ex: 04733434343");
                else {
                    int phone = Integer.parseInt(Phone.getText().toString().trim());
                    SignInDirections.ActionSignIn2ToOptMessage action = SignInDirections.actionSignIn2ToOptMessage();
                    action.setPhone("+" + countryCodeHolder.getSelectedCountryCode() + phone);
                    navigationView.navigate(action);
                }
            }
        });

        conditionLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                long startClickTime = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        startClickTime = (long) Calendar.getInstance().getTimeInMillis();
                        login.getBackground().setColorFilter(0x17000000, PorterDuff.Mode.SRC_ATOP);
                        login.invalidate();
                        return true;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
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
        FacebookLogin();
    }

    CallbackManager mCallbackManager;
    String TAG = "ERR";

    public void FacebookLogin() {//
        Log.println(Log.ERROR, "ERR", "HERE0");
        mCallbackManager = CallbackManager.Factory.create();
        facebook.setReadPermissions("email", "public_profile");
        facebook.setFragment(this);

//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        mAuth = FirebaseAuth.getInstance();
        facebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                Log.println(Log.ERROR, "ERR", "HERE1");
                handleFacebookAccessToken(loginResult.getAccessToken());
                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        getNumber();
                    }
                });
                Bundle bundle= new Bundle();
                bundle.putString("fields","first_name,last_name,email,id");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                Log.println(Log.ERROR,"ERR","HERE9999");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                Log.println(Log.ERROR,"ERR","HERE93292223");
            }
        });
// ...
        Log.println(Log.ERROR,"ERR","dhde");
// Initialize Firebase Auth
    }
    FirebaseAuth mAuth;

    void getNumber()
    {

            //Log.println(Log.ERROR, "ERR", "HERE2");

//            Toast.makeText(getContext(), object.getString("email"), Toast.LENGTH_LONG).show();
            TelephonyManager tMgr = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 1001);
        if(permissionCheck==PackageManager.PERMISSION_GRANTED)
            G_Dialog(tMgr.getLine1Number());
//            Toast.makeText(getContext(),tMgr.getLine1Number(),Toast.LENGTH_LONG).show();

//                            first_name=object.getString("first_name");
//                            last_name = object.getString("last_name");
//                            email = object.getString("email");
//                            id =object.getString("id");
//                            profile="https://graph.facebook.com/"+id+"/picture?type=normal";
//                            constants.simple_login=0;
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    getNumber();
                break;
            default:
                break;
        }

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        Log.println(Log.ERROR,"ERR","HERE3");
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.println(Log.ERROR,"ERR","HERE4");
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Authentication Done.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.println(Log.ERROR,"ERR","HERE5");
                        }
                    }
                });
    }

    void G_Dialog(String str)
    {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.get_phone_number);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView number = dialog.findViewById(R.id.number);
        number.setText(str);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

}