package com.example.coffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 0, whipped=0,chocolate=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */

    public void increment(View view) {

        if(quantity==100)
        {
            Toast.makeText(this, "You cannot have more than 100 cups of Coffee!!",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        TextView quantityTextView = (TextView) findViewById(R.id.number);
       // quantityTextView.setText("" + quantity);
        displayQuantity(quantity);

    }
    public void decrement(View view) {

        if(quantity==1)
        {
            Toast.makeText(this, "You cannot have less than 1 cup of Coffee!!",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        TextView quantityTextView = (TextView) findViewById(R.id.number);
       // quantityTextView.setText("" + quantity);
        displayQuantity(quantity);

    }

    public void submitOrder(View view) {

        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        EditText name = (EditText) findViewById(R.id.name);
        String value = name.getText().toString();
        boolean hasWhippedCream = whippedCream.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price=calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = displayOrderSummary(value, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"Order for : "+value);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(priceMessage);

    }

    /**
     * Calculates the price of the order.
     *
     *
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = quantity * 5;
        whipped=1;
        chocolate=2;
        if(hasWhippedCream)
        {
            price = price + quantity * whipped;
        }
        if(hasChocolate)
        {
            price = price + quantity * chocolate;
        }
        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.number);
        quantityTextView.setText("" + numberOfCoffees);
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.cost);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.cost);
        orderSummaryTextView.setText(message);
    }

    private String displayOrderSummary(String name, int price, boolean addCream, boolean addChocolate) {
        String priceMessage = "Name: "+ name;
        priceMessage += "\nAdd WhippedCream ?" + addCream;
        priceMessage += "\nAdd Chocolate ?" + addChocolate;
        priceMessage += "\nQuantity : " + quantity;
        priceMessage += "\nTotal Price: $" + price;
        priceMessage += "\nThank You so much for coming!!" + "\nVISIT AGAIN!";
        return priceMessage;
    }
}
