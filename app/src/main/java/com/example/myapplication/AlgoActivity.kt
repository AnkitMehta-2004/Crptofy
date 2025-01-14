package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlgoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_algo)





        // Handle Algorithm Button Clicks (AES, DES, Blowfish)
        findViewById<Button>(R.id.btnAES).setOnClickListener {
            // Handle AES selection (show relevant UI or change behavior)
            Toast.makeText(this, "AES selected", Toast.LENGTH_SHORT).show()

            // Automatically start DESActivity when AlgoActivity is opened
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnDES).setOnClickListener {
            // Handle DES selection
            Toast.makeText(this, "DES selected", Toast.LENGTH_SHORT).show()
            // Automatically start DESActivity when AlgoActivity is opened
            val intent = Intent(applicationContext, DESActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnBlowfish).setOnClickListener {
            // Handle Blowfish selection
            Toast.makeText(this, "Blowfish selected", Toast.LENGTH_SHORT).show()
            // Automatically start DESActivity when AlgoActivity is opened
            val intent = Intent(applicationContext, BlowfishActivity::class.java)
            startActivity(intent)
        }







    }
}
