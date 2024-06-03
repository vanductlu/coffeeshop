package tlu.cse.ht63.cf.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tlu.cse.ht63.cf.R;
import tlu.cse.ht63.cf.Activity.CartActivity;
import tlu.cse.ht63.cf.Model.Cart;
import tlu.cse.ht63.cf.Model.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private ArrayList<Product> products ;
    public Cart cart = new Cart();

    private Context mContext;

    public ProductsAdapter(Context context, ArrayList<Product> products) {
        this.mContext = context;
        this.products = products;
    }
    public void addProduct(Product product) {
        products.add(product);
        notifyItemInserted(products.size() - 1);
    }
    public void updateProduct(Product product) {
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            products.set(index, product);
            notifyItemChanged(index);
        }
    }
    public void deleteProduct(Product product) {
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            products.remove(index);
            notifyItemRemoved(index);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  Product p = products.get(position);
        String sProductName = p.getName();
        String sProductDescription = p.getDescription();
        holder.txtProductName.setText(sProductName);
        holder.txtProductDescription.setText(sProductDescription);
        holder.txtPrice.setText(""+p.getPrice());
        holder.idIVSSImage.setImageURI(p.getImage());
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtonClick(view, p);
            }
        });

    }

    private void addButtonClick(View view, Product p) {
        cart.addCart(p);
        showSnackbar(view, mContext.getString(R.string.add_product) + p.getName(), Snackbar.LENGTH_SHORT);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtProductName;
        public TextView txtProductDescription;
        public TextView txtPrice;
        public RelativeLayout relativeLayout;

        public ImageView ivAdd;
        public ImageView idIVSSImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtProductName = (TextView)itemView.findViewById(R.id.idTVName);
            this.txtProductDescription = (TextView)itemView.findViewById(R.id.idTVDescription);
            this.txtPrice = (TextView)itemView.findViewById(R.id.idTVPrice);
            this.ivAdd = (ImageView)itemView.findViewById(R.id.ivAdd);
            this.idIVSSImage = (ImageView)itemView.findViewById(R.id.idIVSSImage);
            this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativelayout);

        }
    }

    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).setAction("Cart", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CartActivity.class);
                mContext.startActivity(intent);
            }
        }).show();

    }

}
