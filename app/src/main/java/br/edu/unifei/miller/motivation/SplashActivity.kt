package br.edu.unifei.miller.motivation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.unifei.miller.motivation.databinding.ActivitySplashBinding
import br.edu.unifei.miller.motivation.infra.MotivationConstants
import br.edu.unifei.miller.motivation.infra.SecurityPreferences

class SplashActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Esconder supportActionar
        // Semelhante ao appbar do Flutter
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding.buttonSave.setOnClickListener(this)

        securityPreferences = SecurityPreferences(this)

        verifyName()
    }

    private fun verifyName() {
        val name = securityPreferences.getString(MotivationConstants.KEY.PERSON_NAME)
        if (name != ""){
            startActivity(Intent(this, MainActivity::class.java))
            // Destrói a splash activity
            finish()
        }
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.button_save) {
            handleSave()
        }
    }

    private fun handleSave() {
        val name = binding.editName.text.toString()

        if (name != "") {
            securityPreferences.storeString(MotivationConstants.KEY.PERSON_NAME, name)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Informe seu nome", Toast.LENGTH_SHORT).show()
        }
    }
}