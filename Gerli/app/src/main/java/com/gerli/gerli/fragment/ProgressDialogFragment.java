package com.gerli.gerli.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressDialogFragment extends DialogFragment {
    private ProgressDialog pDialog;
    static ProgressDialogFragment newInstance(String title) {
        ProgressDialogFragment dlg = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        dlg.setArguments(args);
        return dlg;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle(title);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setProgress(0);

        return pDialog;
    }
    //  更新ProgressDialog顯示的進度, 每呼叫1次就加1
    public void updateProgress() {
        pDialog.incrementProgressBy(1);
    }

}
