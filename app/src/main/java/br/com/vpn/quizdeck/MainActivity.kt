package br.com.vpn.quizdeck

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import br.com.vpn.quizdeck.databinding.ActivityMainBinding
import br.com.vpn.quizdeck.presentation.ui.home.HomeFragment
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        var mInterstitialAd: InterstitialAd? = null
    }

    private lateinit var binding: ActivityMainBinding
    private val INTERSTITIAL_AD_UNIT_ID_END_MATCH by lazy {
        BuildConfig.INTERSTITIAL_AD_UNIT_ID_END_MATCH
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}

//        setupAds()
    }

    private fun setupAds() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            INTERSTITIAL_AD_UNIT_ID_END_MATCH,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.d(TAG, error.toString())
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }
}