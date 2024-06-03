package tlu.cse.ht63.cf.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tlu.cse.ht63.cf.Adapter.CartsAdapter;
import tlu.cse.ht63.cf.Model.Cart;
import tlu.cse.ht63.cf.R;
import tlu.cse.ht63.cf.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rvProduct;
    private TextView tvTotal;
    private Button btnThanhToan;
    private final Cart cart = new Cart();
    private ActivityCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        btnThanhToan = findViewById(R.id.btnThanhtoan); // Khởi tạo nút thanh toán

        tvTotal = binding.tvTotal;

        rvProduct = binding.rvproduct;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rvProduct.setLayoutManager(mLayoutManager);

        CartsAdapter rvAdapter = new CartsAdapter(this, this.cart);
        rvProduct.setAdapter(rvAdapter);
        tvTotal.setText("" + this.cart.getTotalPrice());
        // Xử lý sự kiện click của nút thanh toán
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePayment();
            }
        });
    }
    public void updateData() {

        tvTotal.setText("" + this.cart.getTotalPrice());
    }
    private void handlePayment() {
        if (cart.cartList.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Giỏ hàng của bạn đang trống. Vui lòng thêm sản phẩm vào giỏ hàng trước khi thanh toán.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận thanh toán")
                .setMessage("Bạn có chắc chắn muốn thanh toán không?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý thanh toán, ví dụ: gửi dữ liệu hóa đơn đến một Activity khác
                        Intent intent = new Intent(CartActivity.this, InvoiceActivity.class);
                        intent.putExtra("totalPrice", cart.getTotalPrice());
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnu_main) {
            Log.d("this", "home show here");
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        if (id == R.id.bntLogout) {
            Log.d("this", "logout");
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}