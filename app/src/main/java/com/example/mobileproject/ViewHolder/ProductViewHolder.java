package com.example.mobileproject.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mobileproject.Interface.ItemClickListner;
import com.example.mobileproject.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName,txtProductPrice;
    public ImageView productImage,delete;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);


        productImage = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
        delete = (ImageView) itemView.findViewById(R.id.deleteProduct);

}
    public void setItemClickLitner(ItemClickListner listner){
        this.listner = listner;
    }


    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(),false);
    }
}
