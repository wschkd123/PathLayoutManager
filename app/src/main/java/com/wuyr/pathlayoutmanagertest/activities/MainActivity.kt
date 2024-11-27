package com.wuyr.pathlayoutmanagertest.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.wuyr.pathlayoutmanager.PathLayoutManager
import com.wuyr.pathlayoutmanagertest.R
import com.wuyr.pathlayoutmanagertest.adapters.PathAdapter
import com.wuyr.pathlayoutmanagertest.dpToPx
import com.wuyr.pathlayoutmanagertest.views.CanvasView
import com.wuyr.pathlayoutmanagertest.views.RecBottomView
import java.util.Locale

/**
 * Created by wuyr on 18-5-21 下午11:25.
 */
class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener,
    OnSeekBarChangeListener {
    private var mPathLayoutManager: PathLayoutManager? = null
    private lateinit var mTrackView: CanvasView
    private lateinit var mRecBottomView: RecBottomView
    private var mAdapter: PathAdapter? = null
    private val mToast: Toast by lazy {
        Toast.makeText(this, "", Toast.LENGTH_SHORT)
    }
    private var isPathInitialized = false
    private var isShowPath = false
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main_view)
//        initView()
//        mTrackView.post {
//            setupView()
//        }
    }

    private fun setupView() {
        endDrawClick()
        mAdapter?.apply {
            setType(PathAdapter.TYPE_J20)
            mAdapter?.addData(MutableList(8) { null })
        }

        mPathLayoutManager!!.apply {
            setScrollMode(PathLayoutManager.SCROLL_MODE_LOOP)
            setOrientation(RecyclerView.HORIZONTAL)
            setItemDirectionFixed(true)
            setAutoSelect(true)
            setFlingEnable(false)
            mTrackView.visibility = View.VISIBLE
//            setItemOffset(50.dpToPx(this@MainActivity))
            setItemOffset(114.dpToPx(this@MainActivity)) // 刚好显示5个
            setAutoSelectFraction(0.5f)
            setFixingAnimationDuration(250)
            // 缩放和透明度
            val ratios = floatArrayOf(0.737f, 0f, 0.737f, 0.25f, 1f, 0.5f, 0.737f, 0.75f, 0.737f, 1f)
            setItemScaleRatio(*ratios)
            val alphas = floatArrayOf(0.3f, 0f, 0.7f, 0.25f, 1f, 0.5f, 0.7f, 0.75f, 0.3f, 1f)
            setItemAlpha(*alphas)
        }
    }

    private fun initView() {
        val toggle = ActionBarDrawerToggle(
            this, findViewById(R.id.drawer), findViewById(R.id.toolbar),
            R.string.app_name, R.string.app_name
        )
        (findViewById<View>(R.id.drawer) as DrawerLayout).addDrawerListener(toggle)
        toggle.syncState()
        mTrackView = findViewById(R.id.track_panel)
        mRecBottomView = findViewById(R.id.canvas_view)
//        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
//        recyclerView.layoutManager = PathLayoutManager(null, 150).also { mPathLayoutManager = it }
//        recyclerView.adapter = PathAdapter(this, null).also { mAdapter = it }
        findViewById<View>(R.id.card).isEnabled = false
        findViewById<View>(R.id.normal).isEnabled = false
        (findViewById<View>(R.id.orientation) as Switch).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.direction_fixed) as Switch).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.auto_select) as Switch).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.disable_fling) as Switch).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.show_path) as Switch).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.item_offset) as SeekBar).setOnSeekBarChangeListener(this)
        (findViewById<View>(R.id.auto_select_fraction) as SeekBar).setOnSeekBarChangeListener(this)
        (findViewById<View>(R.id.fixing_animation_duration) as SeekBar).setOnSeekBarChangeListener(
            this
        )
        mPathLayoutManager!!.setOnItemSelectedListener { position: Int ->
            mToast.setText(String.format(Locale.getDefault(), "Item %d selected", position))
            mToast.show()
        }
    }

    fun handleOnClick(view: View) {
        when (view.id) {
            R.id.start_draw -> {
                mAdapter!!.clearData()
                mRecBottomView.visibility = View.VISIBLE
                mTrackView.visibility = View.INVISIBLE
            }

            R.id.end_draw -> {
                endDrawClick()
            }

            R.id.add -> if (checkIsPathInitialized()) {
                val data: MutableList<Any?> = ArrayList()
                var i = 0
                while (i < 12) {
                    data.add(null)
                    i++
                }
                mAdapter!!.addData(data)
            }

            R.id.remove -> if (checkIsPathInitialized()) {
                var i = 0
                while (i < 10) {
                    mAdapter!!.removeData(mAdapter!!.itemCount - 1)
                    i++
                }
            }

            R.id.card -> if (checkIsPathInitialized()) {
                view.isEnabled = false
                findViewById<View>(R.id.j20).isEnabled = true
                findViewById<View>(R.id.dragon).isEnabled = true
                mAdapter!!.setType(PathAdapter.Companion.TYPE_CARD)
            }

            R.id.j20 -> if (checkIsPathInitialized()) {
                view.isEnabled = false
                findViewById<View>(R.id.card).isEnabled = true
                findViewById<View>(R.id.dragon).isEnabled = true
                mAdapter!!.setType(PathAdapter.Companion.TYPE_J20)
            }

            R.id.dragon -> if (checkIsPathInitialized()) {
                view.isEnabled = false
                findViewById<View>(R.id.card).isEnabled = true
                findViewById<View>(R.id.j20).isEnabled = true
                mAdapter!!.setType(PathAdapter.Companion.TYPE_DRAGON)
            }

            R.id.normal -> if (checkIsPathInitialized()) {
                view.isEnabled = false
                findViewById<View>(R.id.overflow).isEnabled = true
                findViewById<View>(R.id.loop).isEnabled = true
                mPathLayoutManager!!.setScrollMode(PathLayoutManager.SCROLL_MODE_NORMAL)
            }

            R.id.overflow -> if (checkIsPathInitialized()) {
                view.isEnabled = false
                findViewById<View>(R.id.normal).isEnabled = true
                findViewById<View>(R.id.loop).isEnabled = true
                mPathLayoutManager!!.setScrollMode(PathLayoutManager.SCROLL_MODE_OVERFLOW)
            }

            R.id.loop -> if (checkIsPathInitialized()) {
                view.isEnabled = false
                findViewById<View>(R.id.overflow).isEnabled = true
                findViewById<View>(R.id.normal).isEnabled = true
                mPathLayoutManager!!.setScrollMode(PathLayoutManager.SCROLL_MODE_LOOP)
            }

            R.id.apply_scale_ratio -> if (checkIsPathInitialized()) {
                val ratios = floatArrayOf(0.737f, 0f, 0.737f, 0.25f, 1f, 0.5f, 0.737f, 0.75f, 0.737f, 1f)
                mPathLayoutManager!!.setItemScaleRatio(*ratios)
                val alphas = floatArrayOf(0.3f, 0f, 0.7f, 0.25f, 1f, 0.5f, 0.7f, 0.75f, 0.3f, 1f)
                mPathLayoutManager!!.setItemAlpha(*alphas)
            }

            else -> {}
        }
    }

    private fun endDrawClick() {
        val path = mRecBottomView.path
        if (!path.isEmpty) {
//            mRecBottomView.visibility = View.INVISIBLE
            mTrackView.path = mRecBottomView.path
            mTrackView.visibility = if (isShowPath) View.VISIBLE else View.INVISIBLE
            mPathLayoutManager!!.updatePath(mRecBottomView.path)
            isPathInitialized = true
        }
    }

    private fun checkIsPathInitialized(): Boolean {
        if (!isPathInitialized) {
            mToast.setText(R.string.path_not_set)
            mToast.show()
        }
        return isPathInitialized
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (checkIsPathInitialized()) {
            when (buttonView.id) {
                R.id.orientation -> mPathLayoutManager!!.setOrientation(if (isChecked) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL)
                R.id.direction_fixed -> mPathLayoutManager!!.setItemDirectionFixed(isChecked)
                R.id.auto_select -> mPathLayoutManager!!.setAutoSelect(isChecked)
                R.id.disable_fling -> mPathLayoutManager!!.setFlingEnable(!isChecked)
                R.id.show_path -> {
                    isShowPath = isChecked
                    if (isChecked) {
                        if (mRecBottomView.visibility == View.INVISIBLE) {
                            mTrackView.visibility = View.VISIBLE
                        }
                    } else {
                        mTrackView.visibility = View.INVISIBLE
                    }
                }

                else -> {}
            }
        } else {
            buttonView.isChecked = !isChecked
        }
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        when (seekBar.id) {
            R.id.item_offset -> {
                mPathLayoutManager!!.setItemOffset(progress)
                mToast.setText(progress.toString())
            }

            R.id.auto_select_fraction -> {
                val fraction = progress / 100f
                mPathLayoutManager!!.setAutoSelectFraction(fraction)
                mToast.setText(fraction.toString())
            }

            R.id.fixing_animation_duration -> {
                mPathLayoutManager!!.setFixingAnimationDuration(progress.toLong())
                mToast.setText(progress.toString())
            }

            else -> {}
        }
        mToast.show()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
}
