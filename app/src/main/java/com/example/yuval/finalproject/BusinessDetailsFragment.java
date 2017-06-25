package com.example.yuval.finalproject;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuval.finalproject.Model.BusinessUser;
import com.example.yuval.finalproject.Model.Model;
import com.example.yuval.finalproject.Model.ModelFirebase;


public class BusinessDetailsFragment extends Fragment {
    private static final String ARG_PARAM1 = "userId";

    private String userId;
    BusinessUser user;
    ImageView imageView;
    private OnFragmentInteractionListener mListener;



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
        MenuItem menuItem = menu.findItem(R.id.main_add);
        menuItem.setVisible(false);
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

        imageView = (ImageView) contentView.findViewById(R.id.strow_image);
        if (user.getImageBitMap()!=null){
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(user.getImageBitMap(), 0, user.getImageBitMap().length));
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.getImage(user.getImages(), new Model.GetImageListener() {
                    @Override
                    public void onSuccess(Bitmap image) {
                        String tagUrl = imageView.getTag().toString();
                        if (tagUrl.equals(user.getImages())) {
                            imageView.setImageBitmap(image);
                           // progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFail() {
                       // progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
        TextView nameEt = (TextView) contentView.findViewById(R.id.mainNameTv);
        TextView idEt= (TextView) contentView.findViewById(R.id.mainIdTv);
        TextView addressEt= (TextView) contentView.findViewById(R.id.detailsAddressTv);
        TextView phoneEt= (TextView) contentView.findViewById(R.id.detailsPhoneTv);
        // TextView timeEt= (TextView) contentView.findViewById(R.id.detail_input_time1);
        //TextView dateEt= (TextView) contentView.findViewById(R.id.detail_input_date);
        //CheckBox cb = (CheckBox) contentView.findViewById(R.id.detail_check_box);
        nameEt.setText(user.getfirstName());
        idEt.setText(user.getUserId());
        addressEt.setText(user.getAddress());
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
            case android.R.id.home:
                Log.d("TAG","home");
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
