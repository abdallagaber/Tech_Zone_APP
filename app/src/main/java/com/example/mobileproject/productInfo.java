package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class productInfo extends AppCompatActivity {

    private ImageView productimage;
    private TextView pname,pdescription,productprice;
    private Button btn_cart;
    String productid = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productid = getIntent().getStringExtra("id");

        btn_cart = (Button) findViewById(R.id.add_cart);
        pname = (TextView) findViewById(R.id.txtname);
        pdescription = (TextView) findViewById(R.id.txtdescription);
        productprice = (TextView) findViewById(R.id.txtprice);
        productimage = (ImageView) findViewById(R.id.imageView);

        getProductInfo(productid);
    }

    private void getProductInfo(String productid) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(productid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    product product = snapshot.getValue(product.class);

                    pname.setText(product.getName());
                    pdescription.setText(product.getDescription());
                    productprice.setText(product.getPrice()+" LE");
                    Picasso.get().load(product.getImage()).into(productimage);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}