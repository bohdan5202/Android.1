package com.example.lab1

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab1.databinding.ActivityMainBinding
import kotlin.toString

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState?.let { s ->
            binding.etName.setText(s.getString("imie_text"))
            binding.etSurname.setText(s.getString("nazwisko_text"))
            binding.etNubmers.setText(s.getString("liczba_text"))

            binding.etName.error = s.getString("err_imie")
            binding.etSurname.error = s.getString("err_nazwisko")
            binding.etNubmers.error = s.getString("err_liczba")

            binding.btnOceny.visibility = s.getInt("btn_visibility", View.VISIBLE)
        }
        setupListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("imie_text", binding.etName.text?.toString())
        outState.putString("nazwisko_text", binding.etSurname.text?.toString())
        outState.putString("liczba_text", binding.etNubmers.text?.toString())

        outState.putString("err_imie", binding.etName.error?.toString())
        outState.putString("err_nazwisko", binding.etSurname.error?.toString())
        outState.putString("err_liczba", binding.etNubmers.error?.toString())

        outState.putInt("btn_visibility", binding.btnOceny.visibility)
    }

    private fun openSecodActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("NUM", binding.etNubmers.text.toString().toInt())
        startActivity(intent)
    }

    private fun setupListeners() {
        val fields = listOf(
            Triple(binding.etName, getString(R.string.blad_imie)) { text: String -> text.isNotBlank() },
            Triple(binding.etSurname, getString(R.string.blad_nazwisko)) { text: String -> text.isNotBlank() },
            Triple(binding.etNubmers, getString(R.string.blad_liczba_ocen)) { text: String ->
                text.toIntOrNull()?.let { it in 5..15 } ?: false
            }
        )

        fields.forEach { (editText, errorMsg, validator) ->
            editText.addTextChangedListener(createTextWatcher {
                validateField(editText, errorMsg, validator)
                validateAllFields(fields)
            })

            editText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    validateField(editText, errorMsg, validator)
                }
            }
        }

        binding.btnOceny.setOnClickListener {
            openSecodActivity()        }
    }

    private fun createTextWatcher(afterTextChanged: () -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged()
            }
        }
    }

    private fun validateField(
        editText: EditText,
        errorMessage: String,
        validator: (String) -> Boolean
    ): Boolean {
        val text = editText.text.toString().trim()
        return if (!validator(text)) {
            editText.error = errorMessage
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            false
        } else {
            editText.error = null
            true
        }
    }

    private fun validateAllFields(fields: List<Triple<EditText, String, (String) -> Boolean>>) {
        val allValid = fields.all { (editText, _, validator) ->
            validator(editText.text.toString().trim())
        }
        binding.btnOceny.visibility = if (allValid) View.VISIBLE else View.GONE
    }


}