package com.example.eautosalon.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eautosalon.R;
import com.example.eautosalon.data.RatingsVM;
import com.example.eautosalon.data.VehicleListingVM;
import com.example.eautosalon.helpers.FragmentUtilities;
import com.example.eautosalon.helpers.MyApiRequest;
import com.example.eautosalon.helpers.MyImageConverter;
import com.example.eautosalon.helpers.MyListView;
import com.example.eautosalon.helpers.MyRunnable;
import com.example.eautosalon.helpers.MySession;


public class VehicleRatingFragment extends Fragment {

    private String vehicleId;
    private String mParam2;
    private ListView lvRatings;
    private View view;
    private Button btn_submit_review;
    private RatingBar ratingBar;
    private TextView txtComment;
    private ImageView imgVehicleImage;
    private String image;


    public VehicleRatingFragment() {
        // Required empty public constructor
    }

    public static VehicleRatingFragment newInstance() {
        VehicleRatingFragment fragment = new VehicleRatingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vehicleId = getArguments().getString("vehicleId");
            image = getArguments().getString("image");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_vehicle_rating, container, false);

        txtComment = view.findViewById(R.id.txt_commentBox);
        btn_submit_review = view.findViewById(R.id.btn_submit_review);
        ratingBar = view.findViewById(R.id.ratingBar);
        imgVehicleImage = view.findViewById(R.id.imgVehicleImage);
        if(image != null)
            imgVehicleImage.setImageBitmap(MyImageConverter.decodeFromBase64ToBitmap(image));


        btn_submit_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = txtComment.getText().toString();
                if(comment.isEmpty() || ratingBar.getRating() == 0){
                    Toast.makeText(getActivity(), "Please add a comment and a rating before submitting!", Toast.LENGTH_LONG).show();
                    return;
                }

                RatingsVM outerClass = new RatingsVM();
                RatingsVM.Ratings newRating = outerClass.new Ratings();
                newRating.VehicleId = Integer.parseInt(vehicleId);
                newRating.UserId = MySession.GetUser().UserId;
                newRating.Comment = comment;
                newRating.RatingMark = (double) ratingBar.getRating();
                addRatingTask(newRating);
            }
        });

        loadReviewsTask(inflater);

        return view;
    }

    private void addRatingTask(RatingsVM.Ratings rating) {
        MyRunnable<RatingsVM.Ratings> myCallback = new MyRunnable<RatingsVM.Ratings>() {
            @Override
            public void run(RatingsVM.Ratings ratingsVM) {
                addRating(ratingsVM);
            }
        };

        MyApiRequest.post(getActivity(), "/api/vehicles/ratings", rating, myCallback, view);
    }

    private void addRating(RatingsVM.Ratings ratingsVM) {
        Toast.makeText(getActivity(), "Review has been added!", Toast.LENGTH_LONG).show();

        Fragment vehicleRating = new VehicleRatingFragment();
        vehicleRating.setArguments(getArguments());
        FragmentUtilities.addFragment(getActivity(), vehicleRating, R.id.vehicle_listing_placeholder, true);
    }

    private void loadReviewsTask(final LayoutInflater inflater) {

        MyRunnable<RatingsVM> myCallback = new MyRunnable<RatingsVM>() {
            @Override
            public void run(RatingsVM ratingsVM) {
                inflateReviews(ratingsVM, inflater);
                //loadReviews(ratingsVM);
            }
        };
        MyApiRequest.get(getActivity(), "/api/vehicles/ratings/" + vehicleId, myCallback, view);

    }

    private void inflateReviews(final RatingsVM ratingsVM, LayoutInflater inflater){
        try{
            LinearLayout item = view.findViewById(R.id.vehicle_reviews);
            View childView;
            for(RatingsVM.Ratings rating: ratingsVM.ratings){
                childView = inflater.inflate(R.layout.rating_item, null);

                TextView txt_reviewer_username = childView.findViewById(R.id.txt_reviewer_username);
                TextView txt_reviewer_date = childView.findViewById(R.id.txt_reviewer_date);
                TextView txt_reviewer_comment = childView.findViewById(R.id.txt_reviewer_comment);
                RatingBar user_reviews = childView.findViewById(R.id.user_reviews);

                txt_reviewer_username.setText(rating.User.FirstName + " " + rating.User.LastName);
                txt_reviewer_comment.setText(rating.Comment);
                txt_reviewer_date.setText(rating.DateReviewed);
                user_reviews.setRating((float)rating.RatingMark);

                item.addView(childView);
            }
        }catch(Exception er){
            Log.e("Err: ", er.getMessage());
        }

    }


    private void loadReviews(final RatingsVM ratingsVM) {

        lvRatings.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return ratingsVM.ratings.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if(view == null){
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.rating_item, parent,false);
                }
                RatingsVM.Ratings rating = ratingsVM.ratings.get(position);

                TextView txt_reviewer_username = view.findViewById(R.id.txt_reviewer_username);
                TextView txt_reviewer_date = view.findViewById(R.id.txt_reviewer_date);
                TextView txt_reviewer_comment = view.findViewById(R.id.txt_reviewer_comment);
                RatingBar user_reviews = view.findViewById(R.id.user_reviews);

                txt_reviewer_username.setText(rating.User.FirstName + " " + rating.User.LastName);
                txt_reviewer_comment.setText(rating.Comment);
                txt_reviewer_date.setText(rating.DateReviewed);

                return view;
            }
        });
    }

}
