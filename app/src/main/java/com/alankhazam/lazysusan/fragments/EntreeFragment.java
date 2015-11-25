package com.alankhazam.lazysusan.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alankhazam.lazysusan.R;
import com.alankhazam.lazysusan.data.Entree;
import com.alankhazam.lazysusan.views.EntreeView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EntreeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EntreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntreeFragment extends Fragment {
    // Fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "ENTREE";

    private Entree mEntree;

    private OnFragmentInteractionListener mListener;

    public EntreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param entree Entree to display
     * @return A new instance of fragment EntreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntreeFragment newInstance(Entree entree) {
        EntreeFragment fragment = new EntreeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, entree);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEntree = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_entree, container, false);
        EntreeView entreeView = (EntreeView) rootView.findViewById(R.id.entree_view);
        entreeView.setEntree(mEntree);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
