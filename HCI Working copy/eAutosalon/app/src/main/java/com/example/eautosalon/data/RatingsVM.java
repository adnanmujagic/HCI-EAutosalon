package com.example.eautosalon.data;

import java.util.List;

public class RatingsVM {

    public class Ratings{
        public String Comment;
        public double RatingMark;
        public String DateReviewed;
        public int UserId;
        public int VehicleId;
        public UserVM User;
    }

    public List<Ratings> ratings;
}
