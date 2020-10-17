package io.github.wottrich.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import io.github.wottrich.R
import io.github.wottrich.databinding.ActivityOverviewBinding
import io.github.wottrich.viewModel.OverviewViewModel

class OverviewActivity : AppCompatActivity() {

    private val viewModel by viewModels<OverviewViewModel>()
    private lateinit var binding: ActivityOverviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBinding()
        setupListeners()
        setupObservers()
    }

    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = this@OverviewActivity
            viewModel = this@OverviewActivity.viewModel
        }
    }

    private fun setupListeners() {
        val mViewModel = viewModel
        binding.apply {

            btnGenerate.setOnClickListener {
                if (mViewModel.validMinMaxNumbers) {
                    mViewModel.generateNumbers()
                } else {
                    Toast.makeText(
                        this@OverviewActivity,
                        "Numero minimo não pode ser maior que o numero maximo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            tvNumbers.setOnClickListener {
                copyText()
            }

            tvInfoCopy.setOnClickListener {
                copyText()
            }

        }

    }

    private fun setupObservers() {
        viewModel.finalNumbers.observe(this) {
            val string = it ?: "Ocorreu um erro"
            binding.tvNumbers.text = string
        }
    }

    private fun copyText() {
        val numbers = binding.tvNumbers.text.toString()

        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val clipData = ClipData.newPlainText("Boa sorte!", numbers)
        clipboard.setPrimaryClip(clipData)
        Toast.makeText(this, "Numeros copiados!", Toast.LENGTH_SHORT).show()

    }
}