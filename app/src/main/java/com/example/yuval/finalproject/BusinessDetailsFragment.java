package com.example.yuval.finalproject;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuval.finalproject.Model.BusinessUser;
import com.example.yuval.finalproject.Model.Model;
import com.example.yuval.finalproject.Model.ModelFirebase;

import java.io.ByteArrayOutputStream;


public class BusinessDetailsFragment extends Fragment {
    private static final String ARG_PARAM1 = "userId";

    private String userId;
    BusinessUser user;

    private OnFragmentInteractionListener mListener;
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


    public BusinessDetailsFragment() {
        setHasOptionsMenu(true);
    }

    public static BusinessDetailsFragment newInstance(String userId) {
        BusinessDetailsFragment fragment = new BusinessDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, userId);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit, menu);
        MenuItem editMenuItem = menu.findItem(R.id.main_edit);
        if(user.getUserId().equals(Model.instance.getConnectedUserID()) )
            editMenuItem.setVisible(true);
        else editMenuItem.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            userId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Business Details");

        final View contentView = inflater.inflate(R.layout.fragment_business_details, container, false);
        this.user = Model.instance.getOneUser(userId);

        imageView = (ImageView) contentView.findViewById(R.id.mainImageView);
        final TextView nameEt = (TextView) contentView.findViewById(R.id.fragment_register_fName_editText);
        final TextView nameLEt = (TextView) contentView.findViewById(R.id.fragment_register_lName_editText);
        final TextView businessName=(TextView)contentView.findViewById(R.id.strow_businessName);
        final TextView addreddEt = (TextView) contentView.findViewById(R.id.fragment_register_address_editText);
        nameEt.setText(this.user.getfirstName());
        nameLEt.setText(this.user.getlastName());
        addreddEt.setText(this.user.getAddress());
        businessName.setText(this.user.getBusinessName());
        isBus= (CheckBox)contentView.findViewById(R.id.fragment_register_isBusiness);
        isBus.setChecked(true);
        tablebus= (TableLayout) contentView.findViewById(R.id.fragment_register_table_bus);
        leser= (CheckBox) contentView.findViewById(R.id.fragment_register_Laser_hair_removal);
        nail=(CheckBox) contentView.findViewById(R.id.fragment_register_Gel_nail_polish);

        TextView Treatments = (TextView) contentView.findViewById(R.id.fragment_details_Treat);
        Treatments.setPaintFlags(Treatments.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


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

        if (this.user.getLaserHair()){
            leser.setChecked(true);
        }
        if (this.user.getGelNail()){
            nail.setChecked(true);
        }


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

        imageView.setTag(user.getImages());

        if (user.getImages() != null && !user.getImages().isEmpty() && !user.getImages().equals("")) {
            Log.d("TAG", "user has image "+user.getImages() );
            Model.instance.getImage(user.getImages(), new Model.GetImageListener() {
                @Override
                public void onSuccess(Bitmap image) {
                    String tagUrl = imageView.getTag().toString();
                    Log.d("TAG", "user has image "+tagUrl);
                    if (tagUrl.equals(user.getImages())) {
                        imageView.setImageBitmap(image);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        user.setImageBitMap(byteArray);
                        //progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFail() {
                    // progressBar.setVisibility(View.GONE);
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Model.instance.downloadPicture(user.getImageBitMap(), user.getfirstName()+".jpeg");
                    Toast.makeText(getActivity(), "Picture is saved!",
                            Toast.LENGTH_LONG).show();
                }
            });
        }



        Log.d("TAG", "got student name: " + user.getfirstName());
        return contentView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.main_edit:
                mListener.onButtonSelected(user.getUserId());
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BusinessDetailsFragment.OnFragmentInteractionListener) {
            mListener = (BusinessDetailsFragment.OnFragmentInteractionListener) context;
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
        void onButtonSelected(String itemId);
    }
}
