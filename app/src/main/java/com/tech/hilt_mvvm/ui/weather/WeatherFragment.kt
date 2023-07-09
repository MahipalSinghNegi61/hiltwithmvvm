package com.tech.hilt_mvvm.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.tech.hilt_mvvm.R
import com.tech.hilt_mvvm.base.BaseFragment
import com.tech.hilt_mvvm.databinding.FragmentWeatherBinding
import com.tech.hilt_mvvm.ui.notification.NotificationViewModel
import com.tech.hilt_mvvm.ui.notification.adapter.ProductListAdapter
import com.tech.hilt_mvvm.utils.setGone
import com.tech.hilt_mvvm.utils.setVisible
import com.tech.hilt_mvvm.utils.showWarningMsg
import javax.inject.Inject


class WeatherFragment : BaseFragment() {
    private lateinit var  binding:FragmentWeatherBinding
    @Inject
    lateinit var viewModel: WeatherViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        viewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]

        binding.submitButton.setOnClickListener {
            if (binding.editText.text.toString().trim().isNotEmpty()){
                viewModel.fetchCurrentWeather(binding.editText.text.toString())
            }
            else{
                showWarningMsg(requireActivity(), getString(R.string.please_enter_country))
            }
        }
        setObserver(viewModel)
    }

    private fun setObserver(viewModel: WeatherViewModel) {
        viewModel.progressBarVisibility.observe(viewLifecycleOwner){ progressBarVisibility ->
            if (progressBarVisibility){
                binding.progressBar.setVisible()
            }
            else{
                binding.progressBar.setGone()
            }
        }

        viewModel.errorMsg.observe(viewLifecycleOwner){ errorMsg ->
            if (errorMsg.isNotEmpty()) {
                showWarningMsg(requireActivity(), errorMsg)
            }
        }


        viewModel.productResponse.observe(viewLifecycleOwner){
            run {
                binding.nameTextView.text = it.location?.name
                binding.regionText.text = it.location?.region
                binding.celciusTextView.text = it.current?.tempC.toString()
                binding.fehrenheitTextView.text = it.current?.tempF.toString()
                binding.localTimeTextView.text = it.current?.lastUpdated
                binding.countryText.text = it.location?.country
                binding.timeZoneTextView.text = it.location?.tzId
            }
        }
    }

}