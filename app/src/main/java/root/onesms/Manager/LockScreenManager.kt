package root.onesms.Manager

import android.app.*
import android.content.*
import android.graphics.*
import android.view.*
import kotlinx.android.synthetic.main.view_lockscreen.view.*
import kotlinx.android.synthetic.main.view_unlockscreen.view.*
import root.onesms.*
import root.onesms.Util.*

/**
 * Created by root1 on 2017. 10. 26..
 */
class LockScreenManager(val context: Service, val soundManager: SoundManager) {

    lateinit var windowManager: WindowManager
    lateinit var inflator: LayoutInflater
    lateinit var pref: SharedPreferences

    val param = WindowManager.LayoutParams (
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT )

    var lockScreen: View? = null
    var unLockScreen: View? = null

    init {
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        pref = UtilClass.getPreference(context)

        createLockScreen()
    }

    private fun createUnLockScreen(){
        unLockScreen = inflator.inflate(R.layout.view_unlockscreen, null).apply {
            windowManager.addView(this, param)
        }

        with(unLockScreen!!){
            cancelButton.setOnClickListener {
                windowManager.removeView(unLockScreen)
                unLockScreen = null
            }
            checkButton.setOnClickListener {
                if (passwordEdit.text.toString() == pref.getString("${R.string.info_open}", "")){
                    unLock()
                }else{
                    passwordEdit.setText("")
                    UtilClass.showToast(context, "실패")
                }
            }
        }
    }

    private fun createLockScreen(){
        lockScreen = inflator.inflate(R.layout.view_lockscreen, null)
        windowManager.addView(lockScreen!!, param)

        pref.edit().apply {
            putBoolean("${R.string.key_lock_state}", true)
            apply()
        }

        with(lockScreen!!){
            contactText.text = pref.getString("${R.string.info_contact}", "")
            unlockButton.setOnClickListener { createUnLockScreen() }
        }

    }

    private fun unLock(){
        unLockScreen?.let { windowManager.removeView(unLockScreen!!) }
        lockScreen?.let { windowManager.removeView(lockScreen!!) }
        soundManager.stopSound()
        context.stopSelf()
        pref.edit().apply{
            putBoolean("${R.string.key_lock_state}", false)
            apply()
        }
    }

}