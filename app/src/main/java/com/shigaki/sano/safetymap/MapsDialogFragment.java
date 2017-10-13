package com.shigaki.sano.safetymap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.DialogFragment;
//DialogFragmentのサポートクラスをインポート

public class MapsDialogFragment extends DialogFragment
{

    public static MapsDialogFragment newInstance()
    {
        return new MapsDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.fragment_dialog, null);

        builder.setView(dialogView)
                .setTitle("おれは")
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ((MapsActivity)activity).onTestDialogOKClick();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        MapsDialogFragment.this.dismiss();
                    }
                });

        return builder.create();

    }

}