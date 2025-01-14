package com.example.myapplication

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Encrypt Button Click Listener
        findViewById<Button>(R.id.btnEncrypt).setOnClickListener {
            val inputText = findViewById<EditText>(R.id.edtInput).text.toString()
            val keyText = findViewById<EditText>(R.id.edtKey).text.toString()

            if (keyText.length != 16) {
                // Show error if key length is not 16
                findViewById<TextView>(R.id.tvError).text = "Key must be 16 characters long"
            } else {
                try {
                    // Encrypt the input text
                    val encryptedText = encrypt(inputText, keyText)
                    // Display the encrypted text
                    findViewById<TextView>(R.id.tvEncrypted).text = encryptedText
                    findViewById<TextView>(R.id.tvError).text = ""  // Clear error message
                } catch (e: Exception) {
                    Log.e("AES Error", "Error during encryption", e)
                    findViewById<TextView>(R.id.tvError).text = "Encryption failed"
                }
            }
        }

        // Decrypt Button Click Listener
        findViewById<Button>(R.id.btnDecrypt).setOnClickListener {
            val encryptedText = findViewById<TextView>(R.id.tvEncrypted).text.toString()
            val keyText = findViewById<EditText>(R.id.edtKey).text.toString()

            if (keyText.length != 16) {
                // Show error if key length is not 16
                findViewById<TextView>(R.id.tvError).text = "Key must be 16 characters long"
            } else {
                try {
                    // Decrypt the encrypted text
                    val decryptedText = decrypt(encryptedText, keyText)
                    // Display the decrypted text
                    findViewById<TextView>(R.id.tvDecrypted).text = decryptedText
                    findViewById<TextView>(R.id.tvError).text = ""  // Clear error message
                } catch (e: Exception) {
                    Log.e("AES Error", "Error during decryption", e)
                    findViewById<TextView>(R.id.tvError).text = "Decryption failed"
                }
            }
        }
    }

    // Function to encrypt the data
    private fun encrypt(data: String, key: String): String {
        try {
            // Initialize the cipher for AES encryption
            val cipher = Cipher.getInstance("AES")
            val secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)

            // Encrypt the data and return as Base64 string
            val encryptedBytes = cipher.doFinal(data.toByteArray())
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        } catch (e: Exception) {
            throw Exception("Encryption failed", e)
        }
    }

    // Function to decrypt the data
    private fun decrypt(data: String, key: String): String {
        try {
            // Initialize the cipher for AES decryption
            val cipher = Cipher.getInstance("AES")
            val secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)

            // Decode the Base64 string and decrypt
            val decodedBytes = Base64.decode(data, Base64.DEFAULT)
            val decryptedBytes = cipher.doFinal(decodedBytes)

            return String(decryptedBytes)
        } catch (e: Exception) {
            throw Exception("Decryption failed", e)
        }
    }
}
