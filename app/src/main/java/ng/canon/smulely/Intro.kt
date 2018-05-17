package ng.canon.smulely

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment

class Intro : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val color = ContextCompat.getColor(this@Intro,R.color.colordeep)

        addSlide(AppIntroFragment.newInstance(getString(R.string.stepa), getString(R.string.stepa_sub), R.drawable.a1, color));
        addSlide(AppIntroFragment.newInstance(getString(R.string.stepb), getString(R.string.stepb_sub), R.drawable.a2, color));
        addSlide(AppIntroFragment.newInstance(getString(R.string.stepc), getString(R.string.stepc_sub), R.drawable.a3, color));
        addSlide(AppIntroFragment.newInstance(getString(R.string.stepd), getString(R.string.stepd_sub), R.drawable.a4, color));

        askForPermissions(arrayOf( Manifest.permission.WRITE_EXTERNAL_STORAGE), 3);

        showSkipButton(false);
        setProgressButtonEnabled(false);
        showDoneButton(true)

    }


    override fun onSkipPressed(currentFragment: Fragment) {
        super.onSkipPressed(currentFragment)
        // Do something when users tap on Skip button.
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)

        startActivity(Intent(this@Intro,MainActivity::class.java))
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
    }
}
