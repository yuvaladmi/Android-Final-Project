package com.example.yuval.finalproject;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yuval.finalproject.Dialogs.MyProgressBar;
import com.example.yuval.finalproject.Model.BusinessUser;
import com.example.yuval.finalproject.Model.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BusinessListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BusinessListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusinessListFragment extends Fragment {
    static final int REQUEST_ADD_ID = 1;
    ListView list;
    List<BusinessUser> data;
    BusinessListAdapter businessListAdapter;
    private OnFragmentInteractionListener mListener;
    private MyProgressBar progressBar;
    ImageView imageView;

    public interface Delegate{
        void showProgressBar();
        void hideProgressBar();
    }

    Delegate delegate;
    public void setDelegate(Delegate dlg){
        this.delegate = dlg;
    }

    public BusinessListFragment() {
    }

    public static BusinessListFragment newInstance() {
        BusinessListFragment fragment = new BusinessListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Businesses List");
        View contentView = inflater.inflate(R.layout.fragment_business_list, container, false);
        list = (ListView) contentView.findViewById(R.id.stlist_list);
        data = new LinkedList<BusinessUser>();
        //data = Model.instance.getAllBusinessUsers();
        businessListAdapter = new BusinessListAdapter();

        list.setAdapter(businessListAdapter);


        imageView = (ImageView) contentView.findViewById(R.id.mainImageView);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG","item " + position + "was selected");
                Log.d("TAG","data.get(position).getUserId()==="+data.get(position).getUserId());
                mListener.onItemSelected(data.get(position).getUserId());

            }
        });

        Model.instance().getAllBusinessUsers(new Model.GetAllUsersListener() {
            @Override
            public void onComplete(List<BusinessUser> list) {
                data = list;
                delegate.hideProgressBar();
                businessListAdapter.notifyDataSetChanged();
            }

            @Override
            public void showProgressBar() {
                progressBar.showProgressDialog();
            }

            @Override
            public void hideProgressBar() {
                progressBar.hideProgressDialog();
            }

        });
        return contentView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
        void onItemSelected(String itemId);
    }
    class BusinessListAdapter extends BaseAdapter {
//        private List listData = new LinkedList<>();
        private LayoutInflater inflater = (LayoutInflater) getActivity().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        public BusinessListAdapter(List listData) {
//            this.listData = listData;
//        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.business_list_row, null);
            }
            TextView name = (TextView) convertView.findViewById(R.id.strow_name);
            TextView businessName = (TextView) convertView.findViewById(R.id.strow_businessName);

            final BusinessUser userInPosition = (BusinessUser) data.get(position);
            name.setText(userInPosition.getfirstName()+" "+userInPosition.getlastName());
            businessName.setText(userInPosition.getBusinessName());

            return convertView;
        }
    }
}
