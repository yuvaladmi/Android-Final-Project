package com.example.yuval.finalproject.Model;

import android.app.Activity;
import android.util.Log;

import java.util.List;

/**
 * Created by Yuval on 10/06/2017.
 */

public class Model {

    ModelFirebase modelFirebase=new ModelFirebase();
    private ModelMem modelMem;
    private ModelSQL modelSql;

    final public static Model instance = new Model();
    public static Model instance(){
        return instance;
    }

    private Model(){
        modelMem = new ModelMem();
        modelSql = new ModelSQL(MyApplication.getMyContext());
        modelFirebase = new ModelFirebase();
    }

    public List<BusinessUser> getAllBusinessUsers(){

        if(BusinessUserSQL.getAllStudents(modelSql.getReadableDatabase()).size() == 0){
            Log.d("TAG", "sql null");
            return modelMem.getAllUsers();
        }
        Log.d("TAG","sql not null");
        return BusinessUserSQL.getAllStudents(modelSql.getReadableDatabase());
    }

    public boolean addNewBusinessUser(BusinessUser newUser){
        BusinessUserSQL.addStudent(modelSql.getWritableDatabase(),newUser);
        return true;
    }

    public BusinessUser getOneUser(String uid) {
        //BusinessUserSQL.getUser(modelSql.getReadableDatabase(),uid);
        return modelMem.getOneUser(uid);
    }


    public void deleteStudent(BusinessUser user) {
        BusinessUserSQL.deleteUser(modelSql.getReadableDatabase(),user);
    }
    public boolean setIdCheck(String id){
        if(getOneUser(id) == null){
            return true;
        }else return false;
    }
    public interface LoginListener{
        /**
         * showing progress bar
         */
        void showProgressBar();

        /**
         * hide progress bar
         */
        void hideProgressBar();

        /**
         * make toast with the text:Authentication failed
         * (the message have to be declared inside activity,that why there are no parameters declared inside firebase class)
         */
        void makeToastAuthFailed();

        /**
         * make toast for verify email in firebase with message declared in firebase class
         * @param msg message to show in the toast
         */
        void makeToastVerifyEmail(String msg);

        /**
         * valide the form of register activity
         * @return true if the form is legit false otherwise
         */
        boolean validateFormInRegister();

        /**
         * valide the form of sighIn activity
         * @return true if the form is legit false otherwise
         */
        boolean validateFormInSignIn();

        /**
         * getting the activity that all the things running inside it
         * @return ActivityMain
         */
        Activity getActivity();

        /**
         * printing to log warning
         * @param tag tag to print
         * @param msg message message to print
         * @param tr the stack trace of warning
         */
        void printToLogWarning(String tag,String msg,Throwable tr);

        /**
         * rinting message to log
         * @param tag tag to print
         * @param msg message to print
         */
        void printToLogMessage(String tag,String msg);

        /**
         * printing to log Exception
         * @param tag tag to print
         * @param msg message message to print
         * @param tr the stack trace of warning
         */
        void printToLogException(String tag,String msg,Throwable tr);


        /**
         * if the registration worked,update the buttons(register button disabled and verify email enabled)
         */
        void updateRegisterActivityIfSuccess();

        void goToMainActivity();
        void goToListFragment();
    }

    public interface saveUserRemote
    {
        void saveUserToRemote(BusinessUser user);
    }

    public void addUser(final BusinessUser user, final LoginListener listener)
    {

        Model.saveUserRemote sur=new saveUserRemote() {
            @Override
            public void saveUserToRemote(BusinessUser user) {
                modelFirebase.addUser(user,listener);
            }
        };
        modelFirebase.createAccount(user,listener,sur);

    }

    public void verifyEmail(LoginListener listener)
    {
        modelFirebase.sendEmailVerification(listener);
    }

    public void signInAfterRegister(String email,String password,LoginListener listener)
    {

        modelFirebase.signInAfterRegister(email,password,listener);
    }

    public void signIn(String email,String password,LoginListener listener)
    {

        modelFirebase.signIn(email,password,listener);

    }

    public void checkIfUserAuthonticated(LoginListener loginListener) {
        modelFirebase.checkIfUserAuthonticated(loginListener);
    }
    public interface SignOutListener
    {
        void goToMainActivity();
        void showProgressBar();
        void hideProgressBar();
    }
    public void signOut(SignOutListener signOutListner)
    {
        modelFirebase.signOut(signOutListner);
    }
}
