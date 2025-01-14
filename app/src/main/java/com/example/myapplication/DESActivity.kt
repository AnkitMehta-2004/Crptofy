package com.example.myapplication

import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec

class DESActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desactivity)

        // Set button click listeners for encrypt and decrypt actions
        findViewById<Button>(R.id.btnEncrypt).setOnClickListener {
            val inputText = findViewById<EditText>(R.id.edtInput).text.toString()
            val keyText = findViewById<EditText>(R.id.edtKey).text.toString()

            if (keyText.length != 8) {
                findViewById<TextView>(R.id.tvError).text = "Key must be 8 characters long"
            } else {
                try {
                    val encryptedText = encryptDES(inputText, keyText)
                    findViewById<TextView>(R.id.tvEncrypted).text = encryptedText
                    findViewById<TextView>(R.id.tvError).text = ""
                } catch (e: Exception) {
                    findViewById<TextView>(R.id.tvError).text = "Encryption failed"
                }
            }
        }

        // Decrypt Button Click Listener
        findViewById<Button>(R.id.btnDecrypt).setOnClickListener {
            val encryptedText = findViewById<TextView>(R.id.tvEncrypted).text.toString()
            val keyText = findViewById<EditText>(R.id.edtKey).text.toString()

            if (keyText.length != 8) {
                findViewById<TextView>(R.id.tvError).text = "Key must be 8 characters long"
            } else {
                try {
                    val decryptedText = decryptDES(encryptedText, keyText)
                    findViewById<TextView>(R.id.tvDecrypted).text = decryptedText
                    findViewById<TextView>(R.id.tvError).text = ""
                } catch (e: Exception) {
                    findViewById<TextView>(R.id.tvError).text = "Decryption failed"
                }
            }
        }
    }

    // DES encryption function
    private fun encryptDES(data: String, key: String): String {
        val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")
        val desKeySpec = DESKeySpec(key.toByteArray())
        val keyFactory = SecretKeyFactory.getInstance("DES")
        val secretKey = keyFactory.generateSecret(desKeySpec)

        // Create IV (Initialization Vector)
        val iv = ByteArray(8)  // DES uses 8 bytes for IV
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))

        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    // DES decryption function
    private fun decryptDES(data: String, key: String): String {
        val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")
        val desKeySpec = DESKeySpec(key.toByteArray())
        val keyFactory = SecretKeyFactory.getInstance("DES")
        val secretKey = keyFactory.generateSecret(desKeySpec)

        // Create IV (Initialization Vector)
        val iv = ByteArray(8)  // DES uses 8 bytes for IV
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))

        val decodedBytes = Base64.decode(data, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes)
    }
}
