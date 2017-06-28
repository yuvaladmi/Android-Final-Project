package com.example.yuval.finalproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuval.finalproject.Dialogs.MyProgressBar;
import com.example.yuval.finalproject.Model.BusinessUser;
import com.example.yuval.finalproject.Model.Model;
import com.example.yuval.finalproject.Model.ModelFirebase;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;


public class BusinessEditFragment extends Fragment {
    private static final String ARG_PARAM1 = "studentId";
    BusinessUser user;

    private String userId;
    private BusinessEditFragment.OnFragmentInteractionListener mListener;
    Bitmap imageBitmap;
    ProgressBar progressBar;
    ImageView imageView;

    TableLayout tablebus;
    Boolean flage=true;
    Boolean flageNail=false;
    Boolean flageLeser=false;
    CheckBox isBus;
    CheckBox leser;
    CheckBox nail;

    EditText add;

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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Edit Business");
        final View contentView = inflater.inflate(R.layout.fragment_business_edit, container, false);
        user = Model.instance.getOneUser(userId);
        final EditText nameEt = (EditText) contentView.findViewById(R.id.fragment_register_fName_editText);
        final EditText nameLEt = (EditText) contentView.findViewById(R.id.fragment_register_lName_editText);
        final EditText businessName = (EditText) contentView.findViewById(R.id.strow_businessName);
        final EditText addreddEt = (EditText) contentView.findViewById(R.id.fragment_register_address_editText);
        TextView Treatments = (TextView) contentView.findViewById(R.id.fragment_edit_Treat);
        Treatments.setPaintFlags(Treatments.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        nameEt.setText(user.getfirstName());
        nameLEt.setText(user.getlastName());
        addreddEt.setText(user.getAddress());
        businessName.setText(user.getBusinessName());
        isBus= (CheckBox)contentView.findViewById(R.id.fragment_register_isBusiness);
        isBus.setEnabled(false);
        tablebus= (TableLayout) contentView.findViewById(R.id.fragment_register_table_bus);

        leser= (CheckBox) contentView.findViewById(R.id.fragment_register_Laser_hair_removal);
        if (user.getLaserHair()){
            leser.setChecked(true);
            flageLeser=true;
        }
        nail=(CheckBox) contentView.findViewById(R.id.fragment_register_Gel_nail_polish);
        if (user.getGelNail()){
            nail.setChecked(true);
            flageNail=true;
        }

        isBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flage=!flage;

                if (flage){

                    tablebus.setVisibility(View.GONE);
                }
                else {

                    tablebus.setVisibility(View.VISIBLE);
                }


            }
        });

        leser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flageLeser=!flageLeser;
            }
        });

        nail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flageNail=!flageNail;
            }
        });





        imageView = (ImageView) contentView.findViewById(R.id.mainImageView);
        if (user.getImageBitMap()!=null){
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(user.getImageBitMap(), 0, user.getImageBitMap().length));
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });



        Button saveBtn = (Button) contentView.findViewById(R.id.editSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder a_bu=new AlertDialog.Builder(getActivity());
                a_bu.setCancelable(false);
                a_bu.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                          Log.d("TAG", "st.getId()==" + user.getUserId());
                        if (imageBitmap != null) {
                            Model.instance.saveImage(imageBitmap, user.getUserId() + ".jpeg", new Model.SaveImageListener() {
                                @Override
                                public void complete(String url) {
                                    user.setImages(url);
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                                    byte[] img = bos.toByteArray();
                                    user.setImageBitMap(img);
                                    user.setfirstName(nameEt.getText().toString());
                                    user.setBusiness(flage);

                                    if (flage)
                                    {
                                        user.setAddress(addreddEt.getText().toString());
                                        user.setGelNail(flageNail);
                                        user.setLaserHair(flageLeser);
                                    }

                                    Model.instance.updateUser(user);
                                    mListener.onSaveSelected();
                                    // progressBar.setVisibility(GONE);
                                }

                                @Override
                                public void fail() {
                                    //notify operation fail,...
                                }
                            });
                        }else{
                            user.setfirstName(nameEt.getText().toString());
                            user.setBusiness(flage);

                            if (flage)
                            {
                                user.setAddress(addreddEt.getText().toString());
                                user.setBusinessName(businessName.getText().toString());
                                user.setGelNail(flageNail);
                                user.setLaserHair(flageLeser);
                            }

                            Model.instance.updateUser(user);
                            mListener.onSaveSelected();
                        }

                    }
                });
                a_bu.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        getFragmentManager().popBackStack();
                    }
                });
                AlertDialog alert=a_bu.create();
                alert.setTitle("Do you want to edit the user ?");
                alert.show();


            }
        });

        Button cancelBtn = (Button) contentView.findViewById(R.id.editCancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                mListener.onCancelSelected();
            }
        });

        return contentView;
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
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
        void onCancelSelected();
    }



}
