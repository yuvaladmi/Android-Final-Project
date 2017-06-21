package com.example.yuval.finalproject.Model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

/**
 * Created by Yuval on 10/06/2017.
 */

public class ModelFirebase {

    private static final String TAG = "EmailPassword";


    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    //----firebase storage and realtime databse-------
    private FirebaseDatabase database;//firebase databse reference

    public StorageReference mStorageRef;//firebase storage reference(read and write images)

    public ModelFirebase() {
        //------------Authentication------------
        mAuth = FirebaseAuth.getInstance();

        //initialize the FirebaseAuth instance and the AuthStateListener method so you can track whenever the user signs in or out.
        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
        database= FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }
    public void createAccount(final BusinessUser user, final Model.LoginListener listener,final Model.saveUserRemote sur) {

        listener.printToLogMessage(TAG, "createAccount:" + user.getEmail());
        if (!listener.validateFormInRegister()) {
            return;
        }

        listener.showProgressBar();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(listener.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        listener.printToLogMessage(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            listener.makeToastAuthFailed();
                            listener.hideProgressBar();
                        }
                        else
                        {
                            listener.updateRegisterActivityIfSuccess();

                            //now adding user to loacl and remote database

                            //enter the token that we got from firebase into userID
                            user.setUserId(mAuth.getCurrentUser().getUid());

                            sur.saveUserToRemote(user);
                        }


                    }
                });
        // [END create_user_with_email]


    }

    public void addUser(BusinessUser user,final Model.LoginListener viewlistener)
    {
        viewlistener.printToLogMessage("TAG","Image was saved to Firebase storage");

        //saving user deatails to storage
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName",user.getfName());
        result.put("lastName",user.getlName());


        DatabaseReference myRef = database.getReference("users").child(user.getUserId());
        myRef.setValue(result);

        viewlistener.hideProgressBar();



    }

    /**
     * send email verification to verify user email
     * @param listener listener which generates functions from the Activity
     */
    public void sendEmailVerification(final Model.LoginListener listener) {

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        //first checking that we make account
        if (fbUser != null) {
            fbUser.sendEmailVerification()
                    .addOnCompleteListener(listener.getActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // [START_EXCLUDE]
                            // Re-enable button

                            if (task.isSuccessful()) {
                                listener.makeToastVerifyEmail("Verification email sent to " + fbUser.getEmail());
                            } else {
                                listener.printToLogException(TAG, "sendEmailVerification", task.getException());
                                listener.makeToastVerifyEmail("Failed to send verification email.");
                            }
                            // [END_EXCLUDE]
                        }
                    });
        }
        else
        {
            listener.makeToastVerifyEmail("Can't verify Email before making account");
        }
        // [END send_email_verification]
    }

    public void signInAfterRegister(String email, String password,final Model.LoginListener listener)
    {

        //you have to sign out  and then sign in,in order to get the new email verified status
        mAuth.signOut();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        listener.printToLogMessage(TAG,"signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            listener.printToLogWarning(TAG, "signInWithEmail:failed", task.getException());
                            listener.makeToastAuthFailed();

                        }
                        FirebaseUser fbUser=mAuth.getCurrentUser();
                        if(fbUser!=null)
                        {
                            if(mAuth.getCurrentUser().isEmailVerified())
                            {
                                listener.goToListFragment();//moved to changeStatus in firebase
                            }
                            else
                            {
                                listener.makeToastVerifyEmail("Please verify email first before signing in");
                                //signOut();

                            }

                        }
                        listener.hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]

    }
    public void signIn(String email, String password, final Model.LoginListener listener) {
        listener.printToLogMessage(TAG, "signIn:" + email);
        if (!listener.validateFormInSignIn()) {
            return;
        }

        listener.showProgressBar();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        listener.printToLogMessage(TAG,"signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            listener.printToLogWarning(TAG, "signInWithEmail:failed", task.getException());
                            listener.makeToastAuthFailed();

                        }
                        else
                        {
                            listener.goToListFragment();
                        }
                        listener.hideProgressBar();

                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    public void checkIfUserAuthonticated(Model.LoginListener loginListener) {
        if(mAuth.getCurrentUser()!=null)
        {
            return;
            //loginListener.goToMainActivity();
        }
    }

    public void signOut(Model.SignOutListener signOutListener) {

        signOutListener.showProgressBar();
        mAuth.signOut();
        signOutListener.hideProgressBar();
        mAuth.removeAuthStateListener(mAuthListener);
        signOutListener.goToMainActivity();
    }

}
