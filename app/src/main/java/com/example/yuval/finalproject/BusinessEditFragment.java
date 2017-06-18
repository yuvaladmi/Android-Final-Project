package com.example.yuval.finalproject;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yuval.finalproject.Model.BusinessUser;
import com.example.yuval.finalproject.Model.Model;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BusinessEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BusinessEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusinessEditFragment extends Fragment {
    private static final String ARG_PARAM1 = "studentId";
    BusinessUser user;
    public int flag = 0;
    private String userId;
    private BusinessEditFragment.OnFragmentInteractionListener mListener;

    public BusinessEditFragment() {
        setHasOptionsMenu(true);
    }

    public static BusinessEditFragment newInstance(String userId) {
        BusinessEditFragment fragment = new BusinessEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_PARAM1);
        }
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.blank, menu);
        MenuItem menuItem = menu.findItem(R.id.main_add);
        menuItem.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Edit Business");
        View contentView = inflater.inflate(R.layout.fragment_business_edit, container, false);
        user = Model.instance.getOneUser(userId);
        Log.d("TAG","EditItemFragment==="+userId);
        final EditText nameEt = (EditText) contentView.findViewById(R.id.editNameTv);
        final EditText idEt = (EditText) contentView.findViewById(R.id.editIdTv);
        final EditText addreddEt = (EditText) contentView.findViewById(R.id.editAddressTv);
        final EditText phoneEt = (EditText) contentView.findViewById(R.id.editPhoneTv);
        //final MyTimePicker timePicker = (MyTimePicker) contentView.findViewById(R.id.edit_input_time1);
        //final MyDatePicker datePicker = (MyDatePicker) contentView.findViewById(R.id.edit_input_date);
        /*final CheckBox cbEt = (CheckBox) contentView.findViewById(R.id.edit_check_box);
        cbEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st.setChecked(!st.getChecked());
                flag = 1;
            }
        });*/

        nameEt.setText(user.getName());
        idEt.setText(user.getUserId());
        addreddEt.setText(user.getAddress());
        /*phoneEt.setText(user.getPhone());
        cbEt.setChecked(user.getChecked());
        if(st.getTime() != null)
            timePicker.onTimeSet(st.getTime().hour, st.getTime().min);
        if(st.getDate() != null)
            datePicker.onDateSet(st.getDate().year,st.getDate().month,st.getDate().day);*/
        Button saveBtn = (Button) contentView.findViewById(R.id.editSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","idEt.getText()=="+idEt.getText().toString());
                Log.d("TAG","st.getId()=="+user.getUserId());
                if(!idEt.getText().toString().equals(user.getUserId()) &&
                        !Model.instance.setIdCheck(idEt.getText().toString())){
                    Toast.makeText(getActivity(), "Student Already exists!", Toast.LENGTH_LONG).show();
                }else{
                    Log.d("TAG","save");
                    user.setUserId(idEt.getText().toString());
                    user.setName(nameEt.getText().toString());
                    user.setAddress(addreddEt.getText().toString());
                    /*user.setPhone(phoneEt.getText().toString());
                    user.setDate(datePicker);
                    user.setTime(timePicker);*/
                    mListener.onSaveSelected();
                }

            }
        });

        Button cancelBtn = (Button) contentView.findViewById(R.id.editCancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(flag == 1){
                    st.setChecked(!st.getChecked());
                    Log.d("TAG","flag = 1");
                }*/
                getFragmentManager().popBackStack();
            }
        });

        Button deleteBtn = (Button) contentView.findViewById(R.id.editDeleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.deleteStudent(user);
                mListener.onDeleteSelected();
            }
        });
        return contentView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BusinessEditFragment.OnFragmentInteractionListener) {
            mListener = (BusinessEditFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSaveSelected();
        void onDeleteSelected();
    }

}
