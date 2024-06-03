package tlu.cse.ht63.cf.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import tlu.cse.ht63.cf.R;

public class InvoiceActivity extends AppCompatActivity {
    private TextView tvInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        tvInvoice = findViewById(R.id.tvInvoice);

        // Lấy tổng giá trị từ Intent
        float totalPrice = getIntent().getFloatExtra("totalPrice", 0);

        // Hiển thị hóa đơn
        tvInvoice.setText("Hóa đơn của bạn:\nTổng giá: " + totalPrice + " VND");
    }
}