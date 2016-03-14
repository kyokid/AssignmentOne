package com.example.havh.assignment_one.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.havh.assignment_one.R;
import com.example.havh.assignment_one.model.InstagramPhoto;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Asus on 3/14/2016.
 */
public class PhotosAdapter extends ArrayAdapter<InstagramPhoto> {


    public PhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhoto photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);

        ImageView ivAvatar = (ImageView) convertView.findViewById(R.id.ivAvatar);

        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);

        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);

        tvLikes.setText(String.valueOf(photo.likesCount));


        ivAvatar.setImageResource(0);

        Picasso.with(getContext()).load(photo.avatarUrl)
                .transform(new CropCircleTransformation())
                .into(ivAvatar);

        tvUsername.setText(photo.username);

        tvCaption.setText(photo.caption);

        ivPhoto.setImageResource(0);

        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.loading_icon).into(ivPhoto);


        return convertView;
    }
}
