package io.github.wottrich.view

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import io.github.wottrich.R
import io.github.wottrich.databinding.ActivityOverviewBinding
import io.github.wottrich.extensions.copyText
import io.github.wottrich.extensions.toast
import io.github.wottrich.viewModel.OverviewViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class OverviewActivity : AppCompatActivity() {

    private val viewModel by viewModel<OverviewViewModel>()
    private lateinit var binding: ActivityOverviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_color)
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
        val overviewActivity = this
        binding.apply {

            btnGenerate.setOnClickListener {
                isValidToGenerate {
                    overviewActivity.viewModel.generateNumbers()
                }
            }

            btnImLucky.setOnClickListener {
                isValidToGenerate {
                    overviewActivity.viewModel.imLucky()
                }
            }

            btnShowNumbers.setOnClickListener {
                handleLuckyNumbersLayout(false)
            }

            tvNumbers.setOnClickListener {
                overviewActivity.copyText(tvNumbers.text.toString())
            }

            tvInfoCopy.setOnClickListener {
                overviewActivity.copyText(tvNumbers.text.toString())
            }

        }

    }

    private fun setupObservers() {
        viewModel.finalNumbers.observe(this) {
            val string = it ?: getString(R.string.error_unknown)
            binding.tvNumbers.text = string
        }

        viewModel.imLuckyNumbers.observe(this) {
            val string = it ?: getString(R.string.error_unknown)
            copyText(string)
            handleLuckyNumbersLayout(true)
            binding.tvNumbers.text = string
        }
    }

    private fun handleLuckyNumbersLayout(show: Boolean) {
        binding.viewHideNumbers.isVisible = show
        binding.btnShowNumbers.isVisible = show
    }

    private fun isValidToGenerate(valid: () -> Unit) {
        if (viewModel.isValidToGenerateNumbers) {
            valid()
        } else {
            toast(viewModel.invalidGenerateNumberMessage())
        }
    }
}