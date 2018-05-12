package group.taihe.newframe

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.soli.pullupdownrefresh.PullRefreshLayout
import com.soli.pullupdownrefresh.more.LoadMoreRecyclerAdapter
import java.util.*

/**
 * @author Soli
 * @Time 2017/9/25
 */
class TestFramgnt : Fragment() {

    data class DataModlue(val index: Int, val color: String)

    private val randColorCode: String
        get() {
            var r: String
            var g: String
            var b: String
            val random = Random()
            r = Integer.toHexString(random.nextInt(256)).toUpperCase()
            g = Integer.toHexString(random.nextInt(256)).toUpperCase()
            b = Integer.toHexString(random.nextInt(256)).toUpperCase()

            r = if (r.length == 1) "0" + r else r
            g = if (g.length == 1) "0" + g else g
            b = if (b.length == 1) "0" + b else b

            return "#" + r + g + b
        }

    private var pos: Int = 0
    private val mData = ArrayList<DataModlue>()
    protected var pageSize = 60
    private var adapter: TestAdapter? = null
    private var mAdapter: LoadMoreRecyclerAdapter? = null
    private var layout: PullRefreshLayout? = null
    private var recyclerView: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val bundle = arguments
        if (bundle != null)
            pos = bundle.getInt("position", 2)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (pos == 2) {
            inflater.inflate(R.layout.test_web, container, false)
        } else {
            inflater.inflate(R.layout.test, container, false)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (pos == 2 && view is NestedScrollView) {
            val webView = view.findViewById<WebView>(R.id.testWebView)
            webView.webViewClient = object : WebViewClient() {
//                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
//                    return true
//                }

                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }
            }
            webView.loadUrl("http://www.baidu.com")
        } else {
            layout = view.findViewById(R.id.refreshLayout)
            recyclerView = view.findViewById(R.id.recyclerView)
            val manager = if (pos == 0) LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) else GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            recyclerView!!.layoutManager = manager

            adapter = TestAdapter()
            mAdapter = LoadMoreRecyclerAdapter(adapter!!)

            if (manager is GridLayoutManager) {
                manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (mAdapter!!.isHeader(position) || mAdapter!!.isFooter(position)) manager.spanCount else 1
                    }
                }
            }

            recyclerView!!.adapter = mAdapter
            //disable scrooll
            layout!!.isEnabled = false
            layout!!.setRefreshListener(object : PullRefreshLayout.onRefrshListener {
                override fun onPullDownRefresh() {
                    mData.clear()
                    addData()
                }

                override fun onPullupRefresh(actionFromClick: Boolean) {
                    addData()
                }
            })

            setData()
        }
    }


    /**
     *
     */
    protected fun setData() {
        for (i in 0 until pageSize) {
            mData.add(DataModlue(i, randColorCode))
        }
        mAdapter!!.notifyDataSetChangedHF()
    }

    protected fun addData() {
        recyclerView!!.postDelayed({
            setData()
            layout!!.onRefreshComplete()
        }, 1000)
    }

    private inner class TestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemCount(): Int {
            return mData.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return TestViewHolder(layoutInflater.inflate(R.layout.test_scroll, parent, false))
        }

        override fun onBindViewHolder(mholder: RecyclerView.ViewHolder, position: Int) {
            val holder = mholder as TestViewHolder
            val module = mData[position]
            holder.text.text = module.index.toString()
            holder.carview.setCardBackgroundColor(Color.parseColor(module.color))
        }

    }

    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var text: TextView
        internal var carview: CardView

        init {
            text = itemView.findViewById(R.id.text)
            carview = itemView.findViewById(R.id.carview)
        }
    }

    companion object {

        /**
         * @param position
         * @return
         */
        fun newInstance(position: Int): TestFramgnt {
            val framgnt = TestFramgnt()
            val bundle = Bundle()
            bundle.putInt("position", position)
            framgnt.arguments = bundle
            return framgnt
        }
    }
}
