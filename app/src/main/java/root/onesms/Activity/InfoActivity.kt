package root.onesms.Activity

import android.annotation.*
import android.os.*
import android.support.v4.app.*
import android.view.*
import android.widget.*
import com.bumptech.glide.*
import kotlinx.android.synthetic.main.activity_info.*
import root.onesms.*
import root.onesms.Util.*

/**
 * Created by root1 on 2017. 10. 27..
 */

class InfoActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        view_pager_info.adapter = InfoViewPagerAdapter(supportFragmentManager)
        button_info_exit.setOnClickListener{ finish() }
    }

    class InfoViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){

        override fun getItem(position: Int): Fragment = ImageFragment(position)

        override fun getCount(): Int = 4

    }

    @SuppressLint("ValidFragment")
    class ImageFragment(val position: Int): Fragment(){

        val resIdArr = arrayOf(R.drawable.info_guide_back_1, R.drawable.info_guide_back_2, R.drawable.info_guide_back_3, R.drawable.info_guide_back_4)

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            Glide.with(this)
                    .load(resIdArr[position])
                    .into(imageView)
            return LinearLayout(context).apply { addView(imageView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT) }
        }

    }
}
