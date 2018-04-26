package group.taihe.newframe

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_state1.*

class AnimationActivity : AppCompatActivity() {

    private val mConstraintSet1 = ConstraintSet()
    private val mConstraintSet2 = ConstraintSet()

    private var isConstraintSet1 = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mConstraintSet1.clone(this@AnimationActivity, R.layout.layout_state1)
        mConstraintSet2.clone(this@AnimationActivity, R.layout.layout_state2)
        setContentView(R.layout.layout_state1)

        change.setOnClickListener {
            TransitionManager.beginDelayedTransition(constraint)
            when (isConstraintSet1) {
                true -> {
                    isConstraintSet1 = false
                    mConstraintSet2.applyTo(constraint)
                }
                false -> {
                    isConstraintSet1 = true
                    mConstraintSet1.applyTo(constraint)
                }
            }
        }
    }
}