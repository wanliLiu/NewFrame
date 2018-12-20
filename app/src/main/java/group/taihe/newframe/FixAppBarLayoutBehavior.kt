package group.taihe.newframe

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller

/*
 * @author soli
 * @Time 2018/12/20 21:59
 */
class FixAppBarLayoutBehavior : AppBarLayout.Behavior {

    private var mScroller: OverScroller? = null

    constructor() : super()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        getParentScroller(context)
    }

    /**
     * 反射获得滑动属性。
     *
     * @param context
     */
    private fun getParentScroller(context: Context) {
        if (mScroller != null) return
        mScroller = OverScroller(context)
        try {
            val reflex_class = javaClass.superclass.superclass.superclass//父类AppBarLayout.Behavior  父类的父类   HeaderBehavior
            val fieldScroller = reflex_class.getDeclaredField("scroller")
            fieldScroller.isAccessible = true
            fieldScroller.set(this, mScroller)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View,
                                dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, type)
        stopNestedScrollIfNeeded(dyUnconsumed, child, target, type)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout,
                                   target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        stopNestedScrollIfNeeded(dy, child, target, type)
    }

    private fun stopNestedScrollIfNeeded(dy: Int, child: AppBarLayout, target: View, type: Int) {
        stopIfFiling()
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            val currOffset = topAndBottomOffset
            if (dy < 0 && currOffset == 0 || dy > 0 && currOffset == -child.totalScrollRange) {
                ViewCompat.stopNestedScroll(target, ViewCompat.TYPE_NON_TOUCH)
            }
        }
    }

    private fun stopIfFiling() {
        if (mScroller != null) { //当recyclerView 做好滑动准备的时候 直接干掉Appbar的滑动
            if (mScroller!!.computeScrollOffset()) {
                mScroller!!.abortAnimation()
            }
        }
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: AppBarLayout, ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN)
            stopIfFiling()
        return super.onTouchEvent(parent, child, ev)
    }
}
