package com.alankhazam.lazysusan.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alankhazam.lazysusan.R;
import com.alankhazam.lazysusan.data.Entree;
import com.alankhazam.lazysusan.http.EntreeSearchTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LazySusanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LazySusanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LazySusanFragment extends Fragment implements EntreeSearchTask.EntreeSearchCallback {
    private OnFragmentInteractionListener mListener;

    private ViewPager mEntreeBrowser;
    private FragmentPagerAdapter mAdapter;
    private EntreeSearchTask mSearchTask;
    private List<Entree> mEntrees;

    public LazySusanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LazySusanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LazySusanFragment newInstance() {
        LazySusanFragment fragment = new LazySusanFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSearchTask = new EntreeSearchTask();
        mSearchTask.setCallback(this);
        mEntrees = new ArrayList<>();
        mAdapter = new EntreeBrowserAdapter(getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lazy_susan, container, false);
        mEntreeBrowser = (ViewPager) rootView.findViewById(R.id.entreeBrowser);

        getEntrees();
        return rootView;
    }

    private void getEntrees() {
        mSearchTask.execute();
    }

    @Override
    public void onEntreeSearchComplete(Collection<Entree> entrees) {
        mEntrees.addAll(entrees);
        mEntreeBrowser.setAdapter(mAdapter);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class EntreeBrowserAdapter extends FragmentPagerAdapter {
        public EntreeBrowserAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            return EntreeFragment.newInstance(mEntrees.get(position));
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return mEntrees.size();
        }
    }
}
