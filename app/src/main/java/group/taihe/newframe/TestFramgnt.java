package group.taihe.newframe;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soli.pullupdownrefresh.PullRefreshLayout;
import com.soli.pullupdownrefresh.more.LoadMoreRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * TODO: 2017/9/25 此处需要输入描述文字
 *
 * @author Soli
 * @Time 2017/9/25
 */
public class TestFramgnt extends Fragment {

    private int pos;
    private List<Integer> mData = new ArrayList<>();
    protected int page = 0;
    protected int pageSize = 40;
    private TestAdapter adapter;
    private LoadMoreRecyclerAdapter mAdapter;
    private RecyclerView recyclerView;
    private PullRefreshLayout layout;

    /**
     * @param position
     * @return
     */
    public static TestFramgnt newInstance(int position) {
        TestFramgnt framgnt = new TestFramgnt();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        framgnt.setArguments(bundle);
        return framgnt;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Bundle bundle = getArguments();
        if (bundle != null)
            pos = bundle.getInt("position", 2);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (pos == 2) {
            NestedScrollView view = new NestedScrollView(getContext());

            LinearLayout layou = new LinearLayout(getContext());
            layou.setOrientation(LinearLayout.VERTICAL);

            TextView tex = new TextView(getContext());
            tex.setText("我是测试的");
            tex.setPadding(60, 60, 60, 60);
            tex.setFocusable(true);
            tex.setFocusableInTouchMode(true);
            tex.setGravity(Gravity.CENTER);
            tex.setBackgroundColor(getContext().getResources().getColor(R.color.green_500));
            layou.addView(tex, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            WebView bridgeWebView = new WebView(getContext());
            bridgeWebView.setFocusable(false);
            bridgeWebView.setFocusableInTouchMode(false);
            bridgeWebView.setId(29302);
            layou.addView(bridgeWebView);
            view.addView(layou);
            return view;
        } else {
//            layout = new PullRefreshLayout(getContext());
//            layout.addView(new RecyclerView(getContext()), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            return layout;

            return inflater.inflate(R.layout.test, null);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (pos == 2 && view instanceof NestedScrollView) {
            WebView webView = (WebView) view.findViewById(29302);
            webView.loadUrl("http://www.baidu.com");
        } else if (view instanceof PullRefreshLayout) {

            layout = view.findViewById(R.id.refreshLayout);
            recyclerView = view.findViewById(R.id.recyclerView);

//            RecyclerView recyclerView = (RecyclerView) ((PullRefreshLayout) view).getChildAt(1);
            RecyclerView.LayoutManager manager = pos == 0 ? new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) : new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);

            adapter = new TestAdapter(getContext());
            mAdapter = new LoadMoreRecyclerAdapter(adapter);

            if (manager instanceof GridLayoutManager){
                ( (GridLayoutManager)manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (mAdapter.isHeader(position) || mAdapter.isFooter(position))
                            return ( (GridLayoutManager)manager).getSpanCount();
                        return 1;
                    }
                });
            }

            recyclerView.setAdapter(mAdapter);
            layout.setPageSize(pageSize);
            layout.setRefreshListener(new PullRefreshLayout.onRefrshListener() {
                @Override
                public void onPullDownRefresh() {
                    page = 0;
                    mData.clear();
                    addData();
                }

                @Override
                public void onPullupRefresh(boolean actionFromClick) {
                    page++;
                    addData();
                }
            });

            refek();
        }
    }

    private void refek() {
        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.autoRefresh();
            }
        }, 1000);
    }

    /**
     *
     */
    protected void setData() {
        for (int i = 0; i < pageSize; i++) {
            mData.add(i);
        }
    }

    protected void addData() {
        layout.postDelayed(() -> {
            setData();
            mAdapter.notifyDataSetChanged();
            layout.onRefreshComplete();
        }, 100);
    }

    private class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context ctx;


        /**
         * @return
         */
        private String getRandColorCode() {
            String r, g, b;
            Random random = new Random();
            r = Integer.toHexString(random.nextInt(256)).toUpperCase();
            g = Integer.toHexString(random.nextInt(256)).toUpperCase();
            b = Integer.toHexString(random.nextInt(256)).toUpperCase();

            r = r.length() == 1 ? "0" + r : r;
            g = g.length() == 1 ? "0" + g : g;
            b = b.length() == 1 ? "0" + b : b;

            return "#" + r + g + b;
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        /**
         * @param mctx
         */
        public TestAdapter(Context mctx) {
            ctx = mctx;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TestViewHolder(getLayoutInflater().inflate(R.layout.test_scroll, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder mholder, int position) {
            TestViewHolder holder = (TestViewHolder) mholder;
            holder.text.setText(String.valueOf(position));
            holder.carview.setCardBackgroundColor(Color.parseColor(getRandColorCode()));
        }

    }

    public class TestViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        CardView carview;

        public TestViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            carview = itemView.findViewById(R.id.carview);
        }
    }
}
