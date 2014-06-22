package com.example.hackrandroid.app;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hackrandroid.app.utils.DisplayUtils;

import java.io.ByteArrayOutputStream;


public class LoginProfileFragment extends Fragment {

  private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
  private ImageView profileImage;
  private Bitmap bitmap;
  private byte[] byteArray;

  public LoginProfileFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

    Button next = (Button) rootView.findViewById(R.id.login_profile_next);


    profileImage = (ImageView) rootView.findViewById(R.id.profile_user_image);
    profileImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    });

      final EditText profileUserName = (EditText) rootView.findViewById(R.id.profile_user_name);
      final EditText profileUserSchool = (EditText) rootView.findViewById(R.id.profile_user_school);

      next.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Fragment frag = new LoginSkillsFragment();
              String tag = frag.getTag();
              FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
              fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
              fragmentTransaction.replace(R.id.container, frag);

              Bundle arguments = getArguments();
              arguments.putString("name", profileUserName.getText().toString());
              arguments.putString("school", profileUserSchool.getText().toString());
              arguments.putByteArray("profileImage", byteArray);
              frag.setArguments(arguments);
              fragmentTransaction.addToBackStack(tag);
              fragmentTransaction.commit();
          }
      });

    return rootView;
  }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();

                // Convert ByteArray to Bitmap::

                bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);

                profileImage.setImageBitmap(DisplayUtils.getCroppedBitmap(bitmap));

            }
        }
    }
}
