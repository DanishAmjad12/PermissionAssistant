package com.aftabsikander.permissionassistant.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aftabsikander.permissionassist.PermissionAssistant;
import com.aftabsikander.permissionassist.interfaces.AfterPermissionGranted;
import com.aftabsikander.permissionassist.interfaces.PermissionCallback;

import java.util.List;

/**
 * Created by afali on 3/30/2017.
 */

/**
 * Created in {@link R.layout#activity_main}
 */
public class SingleCallbackFragment extends Fragment implements PermissionCallback {

    private static final String TAG = "SingleCallbackFragment";
    private static final int RC_SMS_PERM = 122;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Create view
        View v = inflater.inflate(R.layout.fragment_main, container);

        // Button click listener
        v.findViewById(R.id.button_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsTask();
            }
        });

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        PermissionAssistant.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_SMS_PERM)
    private void smsTask() {
        if (PermissionAssistant.hasPermissions(getContext(), Manifest.permission.READ_SMS)) {
            // Have permission, do the thing!
            Toast.makeText(getActivity(), "TODO: SMS things", Toast.LENGTH_LONG).show();
        } else {
            // Request one permission
            PermissionAssistant.requestPermissions(this, getString(R.string.rationale_sms),
                    RC_SMS_PERM, Manifest.permission.READ_SMS);
        }
    }

    @Override
    public void onPermissionsResults(int requestCode, List<String> grantedPerms, List<String> deniedPerms) {
        Log.d(TAG, "onPermissionsResults:" + requestCode + ":granted:" + grantedPerms.size() + ":denied:" + deniedPerms.size());
    }
}
