package coffeeshop.garrulousgirl.in.coffeeshop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {
    int quantity=1;
    String orderSummary=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private void display(int quantity) {
        TextView quantityTextView = findViewById(R.id.quantity_number);
        quantityTextView.setText(" "+quantity);
    }
    public void increment(View view)
    {
        if(quantity<10){
            quantity+=1;
            display(quantity);
        }
        else
            Toast.makeText(MainActivity.this, R.string.max_error, Toast.LENGTH_SHORT).show();

    }

    public void decrement(View view)
    {
        if(quantity>1){
            quantity-=1;
            display(quantity);
        }
        else
            Toast.makeText(MainActivity.this, R.string.min_error, Toast.LENGTH_SHORT).show();
    }
    public void submitOrder(View view)
    {
        displaySummary();
//        sendMail();
        confirm();
    }
    public String displayName()
    {
        EditText userName= findViewById(R.id.username_edit_text);
        String Name=userName.getText().toString();
        return Name;
    }
    public String ToppingsStatus(boolean userWantsWhippedCream, boolean userWantsChocolate)
    {
        StringBuilder status= new StringBuilder();
        if(userWantsChocolate && userWantsWhippedCream)
        {
            status.append("Chocolate and Whipped Cream");
        }
        else if(userWantsChocolate)
        {
            status.append("Chocolate");
        }
        else if(userWantsWhippedCream){
            status.append("Whipped Cream");
        }
        else
        {
            status.append("None");
        }
        return status.toString();
    }

    public  int totalPrice(boolean wc, boolean choco)
    {
        int price;
        price=5;
        if(wc && choco)
        {
            price+=3;
        }
        else if(wc)
        {
            price+=1;
        }
        else if(choco)
        {
            price+=2;
        }
        return price*quantity;
    }

    public void displaySummary()
    {
        CheckBox wc=findViewById(R.id.wc_checkbox);
        boolean userWantsWC=wc.isChecked();
        CheckBox choco=findViewById(R.id.choco_checkbox);
        boolean userWantsChoco=choco.isChecked();
        orderSummary=createOrderSummary(userWantsWC,userWantsChoco);
        TextView summary = findViewById(R.id.orderSummary);
        summary.setText(orderSummary);
    }
    public String createOrderSummary(boolean userWantsWhippedCream, boolean userWantsChocolate)
    {
        StringBuilder stringBuilder= new StringBuilder();
        stringBuilder.append("Name:"+ displayName()+"\n");
        stringBuilder.append("Toppings: " + ToppingsStatus(userWantsWhippedCream, userWantsChocolate)+"\n");
        stringBuilder.append("Quantity: "+quantity+"\n");
        stringBuilder.append("Total: "+ NumberFormat.getCurrencyInstance().format(totalPrice(userWantsWhippedCream,userWantsChocolate)));
        stringBuilder.append("\nThank You "+displayName()+" !!!");
        return stringBuilder.toString();
    }
    public void sendMail() {
        Intent emailIntent = new Intent();
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ameerasherin98@ieee.org"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Order");
        emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }
    public void confirm() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setMessage(R.string.confirmation_msg);
        dialog.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                sendMail();
            }
        });
        dialog.setNegativeButton(R.string.cancel_msg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }
}





