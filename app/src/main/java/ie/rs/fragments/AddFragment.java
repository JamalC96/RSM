package ie.rs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import ie.rs.R;
import ie.rs.activities.Home;
import ie.rs.main.RedSkyMealsApp;
import ie.rs.models.Food;

public class AddFragment extends Fragment {

    private String 		foodName, foodShop;
    private double 		foodPrice, ratingValue;
    private EditText name, shop, price;
    private RatingBar ratingBar;
    private Button saveButton;
    private RedSkyMealsApp app;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (RedSkyMealsApp) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        getActivity().setTitle(R.string.addAFoodLbl);

        name = v.findViewById(R.id.addNameET);
        shop =  v.findViewById(R.id.addShopET);
        price =  v.findViewById(R.id.addPriceET);
        ratingBar =  v.findViewById(R.id.addRatingBar);
        saveButton = v.findViewById(R.id.addFoodBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFood();
            }
        });

        return v;
    }

    public void addFood() {

        foodName = name.getText().toString();
        foodShop = shop.getText().toString();
        try {
            foodPrice = Double.parseDouble(price.getText().toString());
        } catch (NumberFormatException e) {
            foodPrice = 0.0;
        }
        ratingValue = ratingBar.getRating();

        if ((foodName.length() > 0) && (foodShop.length() > 0)
                && (price.length() > 0)) {
            Food c = new Food(foodName, foodShop, ratingValue,
                    foodPrice, false);

            app.dbManager.insert(c);
            startActivity(new Intent(this.getActivity(), Home.class));
        } else
            Toast.makeText(
                    this.getActivity(),
                    "You must Enter Something for "
                            + "\'Name\', \'Shop\' and \'Price\'",
                    Toast.LENGTH_SHORT).show();
    }
}