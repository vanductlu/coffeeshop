package tlu.cse.ht63.cf.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tlu.cse.ht63.cf.Adapter.ProductsAdapter;
import tlu.cse.ht63.cf.Model.Cart;
import tlu.cse.ht63.cf.Model.Product;
import tlu.cse.ht63.cf.R;
import tlu.cse.ht63.cf.Repository.ProductRepository;
import tlu.cse.ht63.cf.databinding.ActivityMainBinding;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ProductRepository productRepository;
    RecyclerView rvProduct;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        productRepository = new ProductRepository(this);
        initData();

        rvProduct = binding.rvproduct;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rvProduct.setLayoutManager(mLayoutManager);

        ProductsAdapter rvAdapter = new ProductsAdapter(this, ProductRepository.getProductList());
        rvProduct.setAdapter(rvAdapter);
    }

    private void initData() {
        String[] names = {"Espresso", "Latte", "Cappuccino", "Americano", "Mocha", "Macchiato", "Flat White", "Affogato", "Irish Coffee", "Turkish Coffee"};
        String[] descriptions = {
                "Rich and bold coffee",
                "Smooth and creamy",
                "Frothy and delicious",
                "Strong and invigorating",
                "Chocolate-flavored coffee",
                "Espresso with a dash of milk",
                "Coffee with steamed milk",
                "Espresso poured over ice cream",
                "Coffee with Irish whiskey",
                "Traditional Turkish coffee"
        };

        int imageCount = countDrawables("ss_", R.drawable.class); // Đếm số lượng ảnh thực tế
        if (imageCount == 0) {
            Log.e("initData", "Không tìm thấy ảnh với tiền tố 'ss_'");
            return;
        }

        for (int i = 0; i < imageCount; i++) { // Chỉ lặp theo số lượng ảnh thực tế
            Product p = new Product();
            String name = names[i % names.length]; // Sử dụng tên từ danh sách
            String description = descriptions[i % descriptions.length]; // Sử dụng mô tả từ danh sách
            int resID = getResId("ss_" + i, R.drawable.class);
            if (resID == -1) {
                Log.e("initData", "Không tìm thấy ID tài nguyên cho ss_" + i);
                continue;
            }
            Uri imgUri = getUri(resID);
            Log.d("initData", "Coffee " + i + " - ResID: " + resID + " - Uri: " + imgUri.toString());
            p.setId(i);
            p.setName(name);
            p.setDescription(description);
            p.setImage(imgUri);
            p.setPrice(Float.parseFloat(String.format("%.2f", new Random().nextFloat() * 1000)));
            productRepository.addProduct(p);
        }
    }
    private int countDrawables(String prefix, Class<?> c) {
        int count = 0;
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().startsWith(prefix)) {
                count++;
            }
        }
        return count;
    }
    public Uri getUri(int resId) {
        return Uri.parse("android.resource://" + this.getPackageName() + "/" + resId);
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnu_cart) {
            Log.d("this", "cart show here");
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
        }
        if (id == R.id.bntLogout) {
            Log.d("this", "logout");
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}