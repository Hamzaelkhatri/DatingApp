package hs.beta.dating;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class opt_message extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_opt_message, container, false);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks  = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NotNull String s, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(s, forceResendingToken);
            mverificationId = s;
            Log.println(Log.ERROR,"SEND SECCESS","JJDEEDE");
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
        {
            String code = phoneAuthCredential.getSmsCode();
            verifyCode(code);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
            Log.println(Log.ERROR,"TAG",e.toString());
        }
    };

    private void verifyCode(String code) {

        try {
            if(mverificationId!=null) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mverificationId, code);
                signInWithPhoneAuthCredential(credential);
            }
            else
                Toast.makeText(getContext(),"Wrong code",Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void sendVerifiaction() {
        String phone = opt_messageArgs.fromBundle(getArguments()).getPhone();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                getActivity(),
                mCallbacks);
    }
    TextView reSend,madeBy;

        private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                {
                                    Toast.makeText(getActivity(), "Good", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Not Good", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

        String mverificationId;
        FirebaseAuth mAuth;
        MaterialEditText v1, v2, v3, v4, v5,v6;
        MaterialButton Verify;
        MotionLayout motionLayout;

        @Override
        public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            assert getArguments() != null;

            v1 = view.findViewById(R.id.v1);
            v2 = view.findViewById(R.id.v2);
            v3 = view.findViewById(R.id.v3);
            v4 = view.findViewById(R.id.v4);
            v5 = view.findViewById(R.id.v5);
            v6 = view.findViewById(R.id.v6);
            madeBy=view.findViewById(R.id.loving);
            final LottieAnimationView animationView = view.findViewById(R.id.animate);
            motionLayout=view.findViewById(R.id.motion_base);
            madeBy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    animationView.setAnimation(R.raw.love);
                    animationView.addAnimatorListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) { }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationView.setAlpha(0f);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            animationView.setAlpha(0f);
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation)
                        { }
                    });
                    animationView.setAlpha(1f);
                    animationView.playAnimation();

//                    Toast.makeText(getContext(),"DONE",Toast.LENGTH_LONG).show();

//                    animationView.clearAnimation();
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });
//                    animationView.playAnimation();
                }
            });
//            motionLayout.setTransition
            mAuth=FirebaseAuth.getInstance();
            reSend=view.findViewById(R.id.reSend);
            reSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    animationView.setAnimation(R.raw.snedit);
                    animationView.addAnimatorListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            animationView.setAlpha(0f);
                            //                 sendVerifiaction();//TODO
                        }

                        @Override
                        public void onAnimationCancel(Animator animation)
                        {
                            animationView.setAlpha(0f);
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation)
                        {

                        }
                    });
                    animationView.setAlpha(1f);
                    animationView.playAnimation();
                }
            });
            Verify = view.findViewById(R.id.verify);
            TextManger();
           // sendVerifiaction();
        }

        private void TextManger() {
            v1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() != 0) {
                        v2.findFocus();
                        v2.requestFocus();
                    }
                }
            });
            v2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 0)
                        v1.requestFocus();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() != 0) {
                        v3.findFocus();
                        v3.requestFocus();
                    }
                }
            });
            v3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 0)
                        v2.requestFocus();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() != 0) {
                        v4.findFocus();
                        v4.requestFocus();
                    }
                }
            });
            v4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 0)
                        v3.requestFocus();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() != 0) {
                        v5.findFocus();
                        v5.requestFocus();
                    }
                }
            });
            v5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 0)
                        v4.requestFocus();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() != 0) {
                        v6.findFocus();
                        v6.requestFocus();
                    }
                }
            });
            v6.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 0)
                        v5.requestFocus();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            Verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String code=v1.getText().toString()+v2.getText().toString()+v3.getText().toString()+v4.getText().toString()+v5.getText().toString()+v6.getText().toString();
                    if(code.length()==6)
                        verifyCode(code);
                    else
                        Toast.makeText(getContext(), "Check the code", Toast.LENGTH_LONG).show();
                }
            });
        }
}
