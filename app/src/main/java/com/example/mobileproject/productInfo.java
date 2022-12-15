package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class productInfo extends AppCompatActivity {

    private ImageView productimage;
    private TextView pname,pdescription,productprice;
    private Button btn_cart;
    private String saveCurrentTime,saveCurrentDate,imageLink;
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

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        final HashMap<String, Object>cartMap = new HashMap<>();
        cartMap.put("date",saveCurrentDate);
        cartMap.put("id",productid);
        cartMap.put("name",pname.getText().toString());
        cartMap.put("price",productprice.getText().toString().replaceAll(" .*", ""));
        cartMap.put("time",saveCurrentTime);
        cartMap.put("image",imageLink);

        cartRef.child("Cart").child(productid).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(productInfo.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                }else {
                    try{
                        throw task.getException();
                    }catch (Exception e){
                        Toast.makeText(productInfo.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


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
                    imageLink = product.getImage();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}