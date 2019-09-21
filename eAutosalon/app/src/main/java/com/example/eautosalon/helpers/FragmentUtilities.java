package com.example.eautosalon.helpers;

import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class FragmentUtilities {

    public static void addFragment(Activity activity, Fragment fragment, int placeholder, boolean addToBackStack) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(placeholder, fragment);

        if(addToBackStack)
            fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    public static void addFragmentAsDialog(Activity activity, DialogFragment fragment) {
        final android.support.v4.app.FragmentManager fm = ((FragmentActivity)activity).getSupportFragmentManager();
        fragment.show(fm, "tag");
    }

}
