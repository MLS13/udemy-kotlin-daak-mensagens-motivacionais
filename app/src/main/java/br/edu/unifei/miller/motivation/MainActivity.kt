package br.edu.unifei.miller.motivation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.edu.unifei.miller.motivation.databinding.ActivityMainBinding
import br.edu.unifei.miller.motivation.infra.MotivationConstants
import br.edu.unifei.miller.motivation.infra.SecurityPreferences
import br.edu.unifei.miller.motivation.repository.Mock

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var securityPreferences: SecurityPreferences
    private var phraseFilter: Int = MotivationConstants.PHRASESFILTER.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        securityPreferences = SecurityPreferences(this)
        binding.textName.text = "Olá, ${securityPreferences.getString(MotivationConstants.KEY.PERSON_NAME)}"

        binding.buttonNewPhrase.setOnClickListener(this)
        binding.imageAll.setOnClickListener(this)
        binding.imageHappy.setOnClickListener(this)
        binding.imageMorning.setOnClickListener(this)

        phraseFilter = MotivationConstants.PHRASESFILTER.ALL
        binding.imageAll.setColorFilter(resources.getColor(R.color.colorAccent, this.theme))
        handleNewPhrase()
    }

    override fun onClick(view: View) {
        val id = view.id
        val listFilter = listOf(R.id.image_all, R.id.image_happy, R.id.image_morning)

        if (id == R.id.button_new_phrase) {
            handleNewPhrase()
        } else if (id in listFilter) {
            handleFilter(id)
        }
    }

    private fun handleFilter(id: Int) {
        when (id) {
            R.id.image_all -> {
                binding.imageAll.setColorFilter(resources.getColor(R.color.colorAccent, this.theme))
                binding.imageHappy.setColorFilter(resources.getColor(R.color.white, this.theme))
                binding.imageMorning.setColorFilter(resources.getColor(R.color.white, this.theme))
                phraseFilter = MotivationConstants.PHRASESFILTER.ALL
            }
            R.id.image_happy -> {
                binding.imageAll.setColorFilter(resources.getColor(R.color.white, this.theme))
                binding.imageHappy.setColorFilter(resources.getColor(R.color.colorAccent, this.theme))
                binding.imageMorning.setColorFilter(resources.getColor(R.color.white, this.theme))
                phraseFilter = MotivationConstants.PHRASESFILTER.HAPPY
            }
            R.id.image_morning -> {
                binding.imageAll.setColorFilter(resources.getColor(R.color.white, this.theme))
                binding.imageHappy.setColorFilter(resources.getColor(R.color.white, this.theme))
                binding.imageMorning.setColorFilter(resources.getColor(R.color.colorAccent, this.theme))
                phraseFilter = MotivationConstants.PHRASESFILTER.MORNING
            }
        }
    }

    private fun handleNewPhrase() {
        binding.textPhrase.text = Mock().getPhrase(phraseFilter)
    }
}