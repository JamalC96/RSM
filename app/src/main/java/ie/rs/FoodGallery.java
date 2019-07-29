package ie.rs;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodGallery extends Fragment {

    private TextView s, b, w, r;
    private ImageView sImg,bImg, wImg, rImg;

    public FoodGallery() {
        //Required empty constructor
    }

    //Instance for hairstyles fragment
    public static FoodGallery newInstance() {
        FoodGallery fragment = new FoodGallery();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflates the Hairstyles fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.food_gallery, container, false);

        s =  v.findViewById(R.id.steaktext);
        b =  v.findViewById(R.id.burgertext);
        w = v.findViewById(R.id.wingstext);
        r = v.findViewById(R.id.ringstext);


        sImg = v.findViewById(R.id.steakimg);
        bImg = v.findViewById(R.id.burgerimg);
        wImg = v.findViewById(R.id.wingsimg);
        rImg = v.findViewById(R.id.ringsimg);

        return v;
    }

}