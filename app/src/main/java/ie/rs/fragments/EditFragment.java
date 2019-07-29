package ie.rs.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ie.rs.R;
import ie.rs.main.RedSkyMealsApp;
import ie.rs.models.Food;

public class EditFragment extends Fragment {

    public boolean isFavourite;
    public Food aFood;
    public ImageView editFavourite;
    private EditText name, shop, price;
    private RatingBar ratingBar;
    public RedSkyMealsApp app;

    private OnFragmentInteractionListener mListener;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(Bundle foodBundle) {
        EditFragment fragment = new EditFragment();
        fragment.setArguments(foodBundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        app = (RedSkyMealsApp) getActivity().getApplication();

        if(getArguments() != null)
            aFood = app.dbManager.get(getArguments().getInt("foodId"));
    }

    private Food getFoodObject(int id) {

        for (Food c : app.dbManager.getAll())
            if (c.foodId == id)
                return c;

        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit, container, false);

        ((TextView)v.findViewById(R.id.editTitleTV)).setText(aFood.foodName);

        name = v.findViewById(R.id.editNameET);
        shop = v.findViewById(R.id.editShopET);
        price = v.findViewById(R.id.editPriceET);
        ratingBar = v.findViewById(R.id.editRatingBar);
        editFavourite = v.findViewById(R.id.editFavourite);

        name.setText(aFood.foodName);
        shop.setText(aFood.shop);
        price.setText(""+ aFood.price);
        ratingBar.setRating((float) aFood.rating);

        if (aFood.favourite == true) {
            editFavourite.setImageResource(R.drawable.ic_favourite_red_on);
            isFavourite = true;
        } else {
            editFavourite.setImageResource(R.drawable.ic_favorite_off);
            isFavourite = false;
        }
        return v;
    }

    public void saveFood(View v) {
        if (mListener != null) {
            String foodName = name.getText().toString();
            String foodShop = shop.getText().toString();
            String foodPriceStr = price.getText().toString();
            double ratingValue = ratingBar.getRating();

            double foodPrice;
            try {
                foodPrice = Double.parseDouble(foodPriceStr);
            } catch (NumberFormatException e)
            {            foodPrice = 0.0;        }

            if ((foodName.length() > 0) && (foodShop.length() > 0) && (foodPriceStr.length() > 0)) {
                aFood.foodName = foodName;
                aFood.shop = foodShop;
                aFood.price = foodPrice;
                aFood.rating = ratingValue;

                app.dbManager.update(aFood);

                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getActivity().getSupportFragmentManager().popBackStack();
                    return;
                }
            }
        } else
            Toast.makeText(getActivity(), "You must Enter Something for Name and Shop", Toast.LENGTH_SHORT).show();
    }

    public void toggle(View v) {

        if (isFavourite) {
            aFood.favourite = false;
            Toast.makeText(getActivity(), "Removed From Favourites", Toast.LENGTH_SHORT).show();
            isFavourite = false;
            editFavourite.setImageResource(R.drawable.ic_favorite_off);
        } else {
            aFood.favourite = true;
            Toast.makeText(getActivity(), "Added to Favourites !!", Toast.LENGTH_SHORT).show();
            isFavourite = true;
            editFavourite.setImageResource(R.drawable.ic_favourite_red_on);
        }
        app.dbManager.update(aFood);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void toggle(View v);
        void saveFood(View v);
    }


}
