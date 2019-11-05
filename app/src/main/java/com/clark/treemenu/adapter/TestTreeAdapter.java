package com.clark.treemenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clark.treemenu.R;
import com.clark.treemenu.utils.Node;
import com.clark.treemenu.utils.TreeHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TestTreeAdapter<T> extends TreeRecyclerAdapter<T> {
    public TestTreeAdapter(Context context, List<T> datas, int defaultExpandLevel) throws IllegalArgumentException, IllegalAccessException {
        super(context, datas, defaultExpandLevel);
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    protected void setItemView(Node node, RecyclerView.ViewHolder holder) {
        TestViewHolder testViewholder = (TestViewHolder) holder;
        if (node.getIcon() == -1) {
            testViewholder.mIcon.setVisibility(View.INVISIBLE);
        } else {
            testViewholder.mIcon.setVisibility(View.VISIBLE);
            testViewholder.mIcon.setImageResource(node.getIcon());
        }
        testViewholder.mText.setText(node.getName());
    }

    /**
     * 动态插入节点
     *
     * @param position
     * @param string
     */
    public void addExtraNode(int position, String string) {
        Node node = mVisibleNodes.get(position);
        int indexOf = mAllNodes.indexOf(node);
        // Node
        Node extraNode = new Node(-1, node.getId(), string);
        extraNode.setParent(node);
        node.getChildren().add(extraNode);
        mAllNodes.add(indexOf + 1, extraNode);

        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
        notifyDataSetChanged();

    }

    class TestViewHolder extends RecyclerView.ViewHolder {
        ImageView mIcon;
        TextView mText;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.id_item_icon);
            mText = itemView.findViewById(R.id.id_item_text);
        }
    }
}
