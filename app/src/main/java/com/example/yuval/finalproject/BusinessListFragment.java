package com.example.yuval.finalproject;

import android.app.Fragment;
import android.content.Context;
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

import com.example.yuval.finalproject.Model.BusinessUser;
import com.example.yuval.finalproject.Model.Model;

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
        data = Model.instance.getAllBusinessUsers();
        businessListAdapter = new BusinessListAdapter();

        list.setAdapter(businessListAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG","item " + position + "was selected");
                mListener.onItemSelected(data.get(position).getUserId());

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
        LayoutInflater inflater = (LayoutInflater) getActivity().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
            if(convertView == null){
                convertView = inflater.inflate(R.layout.business_list_row,null);
                /*CheckBox cb = (CheckBox) convertView.findViewById(R.id.strow_chack_box);
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        Student st = data.get(pos);
                        st.setChecked(!st.getChecked());
                    }
                });*/
            }

            TextView name = (TextView) convertView.findViewById(R.id.strow_name);
            TextView id = (TextView) convertView.findViewById(R.id.strow_id);
            ImageView image = (ImageView) convertView.findViewById(R.id.strow_image);
            //CheckBox cb = (CheckBox) convertView.findViewById(R.id.strow_chack_box);
            //cb.setTag(position);

            BusinessUser bUser = data.get(position);
            name.setText(bUser.getfName());
            id.setText(bUser.getUserId());

            return convertView;
        }
    }
}
