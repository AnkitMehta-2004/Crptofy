package com.example.myapplication

import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class BlowfishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blowfish)

        // Set button click listeners for encrypt and decrypt actions
        findViewById<Button>(R.id.btnEncryptblowfish).setOnClickListener {
            val inputText = findViewById<EditText>(R.id.edtInputblowfish).text.toString()
            val keyText = findViewById<EditText>(R.id.edtKeyblowfish).text.toString()

            if (keyText.isEmpty()) {
                findViewById<TextView>(R.id.tvErrorblowfish).text = "Key cannot be empty"
            } else {
                try {
                    val encryptedText = encryptBlowfish(inputText, keyText)
                    findViewById<TextView>(R.id.tvEncryptedblowfish).text = encryptedText
                    findViewById<TextView>(R.id.tvErrorblowfish).text = ""
                } catch (e: Exception) {
                    findViewById<TextView>(R.id.tvErrorblowfish).text = "Encryption failed"
                }
            }
        }

        // Decrypt Button Click Listener
        findViewById<Button>(R.id.btnDecryptblowfish).setOnClickListener {
            val encryptedText = findViewById<TextView>(R.id.tvEncryptedblowfish).text.toString()
            val keyText = findViewById<EditText>(R.id.edtKeyblowfish).text.toString()

            if (keyText.isEmpty()) {
                findViewById<TextView>(R.id.tvErrorblowfish).text = "Key cannot be empty"
            } else {
                try {
                    val decryptedText = decryptBlowfish(encryptedText, keyText)
                    findViewById<TextView>(R.id.tvDecryptedblowfish).text = decryptedText
                    findViewById<TextView>(R.id.tvErrorblowfish).text = ""
                } catch (e: Exception) {
                    findViewById<TextView>(R.id.tvErrorblowfish).text = "Decryption failed"
                }
            }
        }
    }

    // Blowfish encryption function
    private fun encryptBlowfish(data: String, key: String): String {
        val cipher = Cipher.getInstance("Blowfish")
        val secretKeySpec = SecretKeySpec(key.toByteArray(), "Blowfish")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)

        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    // Blowfish decryption function
    private fun decryptBlowfish(data: String, key: String): String {
        val cipher = Cipher.getInstance("Blowfish")
        val secretKeySpec = SecretKeySpec(key.toByteArray(), "Blowfish")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)

        val decodedBytes = Base64.decode(data, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes)
    }
}
