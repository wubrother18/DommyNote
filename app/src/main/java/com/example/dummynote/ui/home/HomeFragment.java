package com.example.dummynote.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dummynote.DB;
import com.example.dummynote.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private List<?> list;
    private ListView listView;
    private DB mDbHelper;
    private Cursor mNotesCursor;
    OnDataBaseUpdate mCallback;

    public interface OnDataBaseUpdate{
        void onDataBaseUpdate(DB mDbHelper);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback=(OnDataBaseUpdate)context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString()+"要實作 OnImageClickListener");
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        list=new ArrayList<String>(){{
            add("Android");
            add("Android");
            add("Android");
            add("Android");
            add("Android");
            add("Android");
        }};
        listView=root.findViewById(R.id.listView);
        mDbHelper=new DB(getActivity());
        mDbHelper.open();
        mDbHelper.replace(1,"Android",2020-11-04);
        //mDbHelper.delete();
//        ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list);
//        listView.setAdapter(adapter);
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        setAdapter();
        mCallback.onDataBaseUpdate(mDbHelper);
        return root;
    }

    private void setAdapter(){


        mNotesCursor=mDbHelper.getAll();

        String[] from= new String[] {"_id","note","created"};
        int[] to=new int[]{R.id.text1,R.id.text2,R.id.text3};
        SimpleCursorAdapter adapter=
                new SimpleCursorAdapter(getActivity(),
                        R.layout.list_item_1,
                        mNotesCursor,
                        from,
                        to,
                        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }
}