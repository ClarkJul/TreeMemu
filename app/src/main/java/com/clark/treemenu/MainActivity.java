package com.clark.treemenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.clark.treemenu.adapter.TestTreeAdapter;
import com.clark.treemenu.adapter.TreeListViewAdapter;
import com.clark.treemenu.adapter.TreeRecyclerAdapter;
import com.clark.treemenu.bean.FileBean;
import com.clark.treemenu.bean.OrgBean;
import com.clark.treemenu.utils.Node;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView treeMenu;
    private List<FileBean> mDatas;
    private List<OrgBean> mDatas2;
    private TestTreeAdapter<OrgBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        treeMenu = findViewById(R.id.rv_tree_menu);

        initDatas();
        try {
            LinearLayoutManager manager=new LinearLayoutManager(this);
            treeMenu.setLayoutManager(manager);
            mAdapter = new TestTreeAdapter<>(this,
                    mDatas2, 0);
            treeMenu.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        initEvent();

    }

    private void initEvent() {
        mAdapter.setOnTreeNodeClickListener(
                new TreeRecyclerAdapter.OnTreeNodeClickListener() {
                    @Override
                    public void onClick(Node node, int position) {
                        Toast.makeText(MainActivity.this,node.getName(),Toast.LENGTH_SHORT).show();
                    }
                });

        mAdapter.setOnTreeNodeLongClickListener(new TreeRecyclerAdapter.OnTreeNodeLongClickListener() {
            @Override
            public void onLongClick(Node node, final int position) {
                final EditText et = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this).setTitle("Add Node")
                        .setView(et)
                        .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                if (TextUtils.isEmpty(et.getText().toString()))
                                    return;
                                mAdapter.addExtraNode(position, et.getText()
                                        .toString());
                            }
                        }).setNegativeButton("Cancel", null).show();
            }
        });
    }

    private void initDatas() {
        mDatas = new ArrayList<FileBean>();
        FileBean bean = new FileBean(1, 0, "根目录1");
        mDatas.add(bean);
        bean = new FileBean(2, 0, "根目录2");
        mDatas.add(bean);
        bean = new FileBean(3, 0, "根目录3");
        mDatas.add(bean);
        bean = new FileBean(4, 1, "根目录1-1");
        mDatas.add(bean);
        bean = new FileBean(5, 1, "根目录1-2");
        mDatas.add(bean);
        bean = new FileBean(6, 5, "根目录1-2-1");
        mDatas.add(bean);
        bean = new FileBean(7, 3, "根目录3-1");
        mDatas.add(bean);
        bean = new FileBean(8, 3, "根目录3-2");
        mDatas.add(bean);

        // initDatas
        mDatas2 = new ArrayList<OrgBean>();
        OrgBean bean2 = new OrgBean(1, 0, "根目录1");
        mDatas2.add(bean2);
        bean2 = new OrgBean(2, 0, "根目录2");
        mDatas2.add(bean2);
        bean2 = new OrgBean(3, 0, "根目录3");
        mDatas2.add(bean2);
        bean2 = new OrgBean(4, 1, "根目录1-1");
        mDatas2.add(bean2);
        bean2 = new OrgBean(5, 1, "根目录1-2");
        mDatas2.add(bean2);
        bean2 = new OrgBean(6, 5, "根目录1-2-1");
        mDatas2.add(bean2);
        bean2 = new OrgBean(7, 3, "根目录3-1");
        mDatas2.add(bean2);
        bean2 = new OrgBean(8, 3, "根目录3-2");
        mDatas2.add(bean2);
    }
}
