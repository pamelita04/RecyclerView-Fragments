package com.example.pam.listsamsung;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pam.listsamsung.dummy.DummyContent;
import com.example.pam.listsamsung.dummy.DummyContent.DummyItem;
import com.example.pam.listsamsung.dummy.Promotion;
import com.example.pam.listsamsung.tools.FirebaseReferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PromotionFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    FirebaseDatabase database;
    List<Promotion> promotions;
    static final String TAG = "PromotionFragment";
    String userId = "3M7G5MxTsGaRMdjxqVFWiyxGUF22";
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PromotionFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PromotionFragment newInstance(int columnCount) {
        PromotionFragment fragment = new PromotionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion_list, container, false);

        promotions = new ArrayList<>();
        //findUserBranch(userId);
        validateUser(userId);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyPromotionRecyclerViewAdapter(promotions, mListener));
        }
        return view;
    }

    private void validateUser(final String uid){

        final DatabaseReference user = database.getReference(FirebaseReferences.USER_REFERENCE).child(uid);
        user.child("status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String status = dataSnapshot.getValue(String.class);
                if (status.equalsIgnoreCase("ACTIVE")) {
                    findUserBranch(userId);
                }else {
                    Log.i(TAG, "Error! - fragments");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Error!", databaseError.toException());
                Log.i(TAG, "Error user no exists");



            }
        });

    }

    public void findUserBranch(String userId){
        DatabaseReference userBranch = database.getReference(FirebaseReferences.USERBRANCH_REFERENCE
                +"/"+ userId);
        userBranch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {

                    String branchKey = uniqueUserSnapshot.getKey();
                    Log.i(TAG, "UserBranch is:" + "-" + branchKey);
                    findBranchPromo(branchKey);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Error!", databaseError.toException());
                Log.i(TAG, "Error usuario no asignado");
            }
        });
    }

    public void findBranchPromo(final String branchKey){

        final DatabaseReference branchPromoReference = database.getReference(FirebaseReferences.BRANCHPROMO_REFERENCE).child(branchKey);
        branchPromoReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                promotions.removeAll(promotions);

                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {

                    String promoKey = uniqueUserSnapshot.getKey();
                    Log.i(TAG, "promotion key" + "-" + promoKey);

                    findPromo(promoKey);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Error!", databaseError.toException());
            }
        });

    }

    // TODO: 8/8/2017 implemets control active and inactive changes
    public void findPromo(final String promoKey) {

        DatabaseReference promoReference = database.getReference(FirebaseReferences.PROMO_REFERENCE).child(promoKey);
        promoReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int count = promotions.size();
                Promotion promo = dataSnapshot.getValue(Promotion.class);

                promotions.add(count, promo);
                //adapter.notifyDataSetChanged();
                recyclerView.setAdapter(new MyPromotionRecyclerViewAdapter(promotions, mListener));

                String answer = "size: " + promotions.toString() + "-" + promo.getName() + "-" + promo.getEndDate() + "-" + promo.getStatus();
                Log.i(TAG, answer);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.i(TAG, "Error!", databaseError.toException());
            }

        });


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            Toast.makeText(context, "PromotionList Fragment Attached", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Promotion item);
    }
}
