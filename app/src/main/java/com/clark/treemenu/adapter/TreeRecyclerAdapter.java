package com.clark.treemenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.clark.treemenu.utils.Node;
import com.clark.treemenu.utils.TreeHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class TreeRecyclerAdapter<T> extends RecyclerView.Adapter {

    protected Context mContext;
    protected List<Node> mAllNodes;
    protected List<Node> mVisibleNodes;
    protected LayoutInflater mInflater;

    /**
     * 设置Node的点击回调
     */
    public interface OnTreeNodeClickListener {
        void onClick(Node node, int position);
    }

    /**
     * 设置node的长按回调
     */
    public interface OnTreeNodeLongClickListener {
        void onLongClick(Node node, int position);
    }

    private OnTreeNodeClickListener mListener;
    private OnTreeNodeLongClickListener mLongListener;

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener mListener) {
        this.mListener = mListener;
    }
    public void setOnTreeNodeLongClickListener(OnTreeNodeLongClickListener mLongListener) {
        this.mLongListener = mLongListener;
    }

    public TreeRecyclerAdapter(Context context, List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
    }

    /**
     * 点击收缩或者展开
     *
     * @param position
     */
    private void expandOrCollapse(int position)
    {
        Node n = mVisibleNodes.get(position);
        if (n != null)
        {
            if (n.isLeaf())
                return;
            n.setExpand(!n.isExpand());
            mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getItemView(parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final Node node=mVisibleNodes.get(position);
        // 设置内边距
        holder.itemView.setPadding(node.getLevel() * 30, 3, 3, 3);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
                if (mListener != null)
                {
                    mListener.onClick(mVisibleNodes.get(position), position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mLongListener.onLongClick(node,position);
                return true;
            }
        });
        setItemView(node,holder);
    }

    @Override
    public int getItemCount() {
        return mVisibleNodes==null?0:mVisibleNodes.size();
    }

    /**
     * 加载item的布局
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract  RecyclerView.ViewHolder getItemView(ViewGroup parent, int viewType);

    /**
     * 设置item的内容
     * @param node
     * @param holder
     */
    protected abstract void setItemView(Node node, RecyclerView.ViewHolder holder);

}
