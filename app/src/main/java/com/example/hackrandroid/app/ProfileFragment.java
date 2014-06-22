package com.example.hackrandroid.app;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hackrandroid.app.utils.DisplayUtils;
import com.parse.ParseUser;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

  private TextView nameTextView;
  private TextView schoolTextView;
  private Bitmap bitmap;
  private ImageView profileImageView;

  ProfileAdapter adapter;

  ParseUser currentUser;

  ArrayList<ProfileItem> profileItemList;

  public ProfileFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

    currentUser = ParseUser.getCurrentUser();



    //TODO: Get Current User from Parse
    profileItemList = new ArrayList<ProfileItem>();
    profileItemList.add(new ProfileItem("Basic Descriptions", true));
    profileItemList.add(new ProfileItem((String)currentUser.get("name"), false ));
    profileItemList.add(new ProfileItem((String)currentUser.get("school"), false));

    profileItemList.add(new ProfileItem("Skills", true));

    ArrayList<String> skills =(ArrayList<String>) currentUser.get("skills");
    for (int i = 0; i < skills.size(); ++i){
      profileItemList.add(new ProfileItem(skills.get(i), false));
    }

    View header = inflater.inflate(R.layout.profile_header, null, false);

    ImageView profilePic = (ImageView) header.findViewById(R.id.profile_header_image);


    byte[] byteArray = currentUser.getBytes("image");
    bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
            byteArray.length);

    bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);

    profilePic.setImageBitmap(DisplayUtils.getCroppedBitmap(bitmap));
    profilePic.setScaleType(ImageView.ScaleType.CENTER_CROP);

    final ListView profileListView = (ListView) rootView.findViewById(R.id.profile_list);

    adapter = new ProfileAdapter(getActivity(),profileItemList);

    profileListView.addHeaderView(header);
    profileListView.setAdapter(adapter);

    /*profileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch(i) {
                case 1: {
                    Toast.makeText(getActivity(), "hi", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    alert.setTitle("Name");
                    alert.setMessage(ParseUser.getCurrentUser().getUsername());

                    // Set an EditText view to get user input
                    final EditText input = new EditText(getActivity());
                    alert.setView(input);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString();
                            // Do something with value!
                            ParseQuery<ParseUser> query = ParseUser.getQuery();
                            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
                            query.findInBackground(new FindCallback<ParseUser>() {
                                @Override
                                public void done(List<ParseUser> parseUsers, ParseException e) {
                                    if (e == null) {
                                        Log.e("couldnt find user", String.valueOf(parseUsers.size()));
                                        ParseObject parseObject = parseUsers.get(0);
                                        parseObject.put("name", input.getText().toString());
                                        parseObject.saveInBackground();

                                        profileItemList.set(1, new ProfileItem(input.getText().toString(), false));
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Log.e("couldnt find user", "couldnt find user");
                                    }
                                }
                            });
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });

                    alert.show();
                    break;
                }
                case 4: {
                    Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
                    break;
                }
                default: {
                    Toast.makeText(getActivity(), "lol", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }); */

    return rootView;
  }


  private class ProfileAdapter extends ArrayAdapter<ProfileItem>{

    ArrayList<ProfileItem> objects;


    public ProfileAdapter(Context context, ArrayList<ProfileItem> objects) {
      super(context,0, objects);
      this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

      View v = convertView;
      ProfileItem item = objects.get(position);

      if (v == null) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(item.isHeader())
          v = inflater.inflate(R.layout.profile_item_header, null);
        else
          v = inflater.inflate(R.layout.profile_item, null);
      }

      if (item.isHeader()){
        TextView title = (TextView) v.findViewById(R.id.profile_header_text);
        title.setText(item.getLabel());
        DisplayUtils.openSansRegularifyTextView(title);
      }
      else {
        TextView title = (TextView) v.findViewById(R.id.profile_item_text);
        title.setText(item.getLabel());
        DisplayUtils.openSansLightifyTextView(title);

      }

      return v;

    }


  }
}
