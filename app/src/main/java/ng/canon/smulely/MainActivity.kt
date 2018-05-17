package ng.canon.smulely

import android.content.*
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.github.angads25.toggle.LabeledSwitch
import com.github.angads25.toggle.interfaces.OnToggledListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.sand.*
import ng.canon.smulely.Auto.Knight
import android.R.id.edit
import android.content.SharedPreferences
import android.content.Intent



class MainActivity : AppCompatActivity() {
    private var isSvcRunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        konji()

        switches.setOnToggledListener(object: OnToggledListener {
            override fun onSwitched(labeledSwitch: LabeledSwitch?, isOn: Boolean) {

                val box = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)

                if (isOn){

                    open.visibility = View.VISIBLE
                    cover.setImageResource(R.drawable.logos)
                    box.edit().putBoolean("locked", true).apply()
                    Starts()

                }else{

                    open.visibility = View.GONE
                    cover.setImageResource(R.drawable.dark)
                    box.edit().putBoolean("locked", false).apply()
                    Offs()


                }
            }


        })


        setSupportActionBar(toolbars);

        open.setOnClickListener {

            callInstagram(applicationContext,"com.smule.singandroid")
        }


        tuber.setOnClickListener {

            watchYoutubeVideo("_eCr7FkzQsc")
        }


        
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menus, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.getItemId()) {
            R.id.downloads -> {

                startActivity(Intent(this@MainActivity,Silo::class.java))
                return true
            }
            R.id.how -> {
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }



    }

    fun Starts(){

        val intu = Intent(applicationContext, Knight::class.java)
        ContextCompat.startForegroundService(applicationContext,intu)
    }



    fun Offs(){

        val intu = Intent(applicationContext, Knight::class.java)
        stopService(intu)
    }


    fun konji(){
        val t = Thread(Runnable {
            //  Initialize SharedPreferences
            val getPrefs = PreferenceManager
                    .getDefaultSharedPreferences(baseContext)

            //  Create a new boolean and preference and set it to true
            val isFirstStart = getPrefs.getBoolean("firstStart", true)

            //  If the activity has never started before...
            if (isFirstStart) {

                //  Launch app intro
                val i = Intent(this@MainActivity, Intro::class.java)

                runOnUiThread { startActivity(i) }

                //  Make a new preferences editor
                val e = getPrefs.edit()

                //  Edit preference to make it false because we don't want this to run again
                e.putBoolean("firstStart", false)

                //  Apply changes
                e.apply()
            }
        })

        // Start the thread
        t.start()
    }



    override fun onResume() {
        val manager = LocalBroadcastManager.getInstance(applicationContext)
        manager.registerReceiver(mReceiver, IntentFilter(Knight.ACTION_PONG))
        // the service will respond to this broadcast only if it's running
        manager.sendBroadcast(Intent(Knight.ACTION_PING))
        super.onResume()
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(mReceiver);
        super.onStop()
    }


    protected var mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // here you receive the response from the service
            if (intent.action == Knight.ACTION_PONG) {
                isSvcRunning = true
                watchTower()
                cover.setImageResource(R.drawable.logos)
                open.visibility = View.VISIBLE


            }
        }
    }



    fun watchTower(){

        switches.isOn = isSvcRunning

    }

    private fun callInstagram(context: Context, packageN: String) {
        val apppackage = packageN
        try {
            val i = context.packageManager.getLaunchIntentForPackage(apppackage)
            context.startActivity(i)
            finish()
        } catch (e: Exception) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageN)))
        }

    }


    fun watchYoutubeVideo(id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id))
        val webIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id))
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }

    }

}
