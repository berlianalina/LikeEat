package com.example.android.likeeatapplication;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.likeeatapplication.Adapter.PostAdapter;
import com.example.android.likeeatapplication.Model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Leonardo on 3/30/2018.
 */

public class TabFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private PostAdapter postAdapater;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    private ArrayList<Post> listPosts;
    private PostAdapter postList;

    Query databaseFood;

    public TabFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static TabFragment2 newInstance(String param1, String param2) {
        TabFragment2 fragment = new TabFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        recyclerView = view.findViewById(R.id.home);
        recyclerView.setHasFixedSize(true);

        listPosts = new ArrayList<>();
        databaseFood = FirebaseDatabase.getInstance().getReference(MainActivity.table1).orderByChild("like");

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        layoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(layoutManager);

        postList = new PostAdapter(getContext(), listPosts);

        recyclerView.setAdapter(postList);

        loadPost();

        return view;
    }

    public void loadPost(){
        databaseFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listPosts.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Post post = postSnapshot.getValue(Post.class);

                    listPosts.add(post);
                }

                postList.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
