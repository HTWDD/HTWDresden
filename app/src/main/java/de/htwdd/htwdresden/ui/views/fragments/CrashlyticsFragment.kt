package de.htwdd.htwdresden.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import de.htwdd.htwdresden.R
import de.htwdd.htwdresden.interfaces.Swipeable
import de.htwdd.htwdresden.ui.viewmodels.fragments.SettingsViewModel
import de.htwdd.htwdresden.utils.extensions.*
import de.htwdd.htwdresden.utils.holders.CryptoSharedPreferencesHolder
import kotlinx.android.synthetic.main.fragment_crashlytics.*

class CrashlyticsFragment: Fragment(), Swipeable {

    companion object {
        private var delegate: SwipeDelegate? = null
        fun newInstance(delegate: SwipeDelegate? = null) = CrashlyticsFragment().apply {
            this@Companion.delegate = delegate
        }
    }

    private val cph by lazy { CryptoSharedPreferencesHolder.instance }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_crashlytics, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    override fun onResume() {
        super.onResume()
        cph.onChanged().runInUiThread().subscribe {
            when (it) {
                is CryptoSharedPreferencesHolder.SubscribeType.Crashlytics -> checkState()
            }
        }.addTo(disposeBag)
        checkState()
    }

    private fun setup() {
        checkState()
        btnYes.click {
            cph.setCrashlytics(true)
            delegate?.moveNext()
        }
        btnLogin.click {
            findNavController()
                .navigate(R.id.action_to_web_view_page_fragment,
                    bundleOf(WebViewFragment.BUNDLE_ARG_URL to "file:///android_asset/HTW-Datenschutz.html", "title" to getString(R.string.data_protection))
                )
        }
    }

    private fun checkState() {
            lottieAnimationView.apply {
                setAnimation("PulseBlue.json")
                repeatCount = LottieDrawable.INFINITE
                playAnimation()
            }

            if (cph.hasCrashlytics()) {
                btnYes.apply {
                    isEnabled = false
                    text = "✓"
                }

                lottieAnimationView.apply {
                    setAnimation("PulseGray.json")
                    repeatCount = LottieDrawable.INFINITE
                    playAnimation()
                }
            }
    }
}