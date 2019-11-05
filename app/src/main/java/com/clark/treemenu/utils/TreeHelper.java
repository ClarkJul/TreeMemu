package com.clark.treemenu.utils;

import android.util.Log;

import com.clark.treemenu.R;
import com.clark.treemenu.annotation.TreeNodeId;
import com.clark.treemenu.annotation.TreeNodeLabel;
import com.clark.treemenu.annotation.TreeNodePid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TreeHelper {

    public static <T> List<Node> datasToNodes(List<T> datas)
            throws IllegalArgumentException, IllegalAccessException {
        List<Node> nodes = new ArrayList<Node>();
        Node node = null;
        for (T t : datas) {

            int id = -1;
            int pid = -1;
            String label = null;

            node = new Node();

            //获取t的类
            Class clazz = t.getClass();

            //获取该类的所有属性
            Field[] fields = clazz.getDeclaredFields();

            for (Field f : fields) {
                //获取含有TreeNodeId的注解的属性
                if (f.getAnnotation(TreeNodeId.class) != null) {
                    f.setAccessible(true);//默认外部不可使用，setAccessible为true，强制调用
                    id = f.getInt(t);
                }

                if (f.getAnnotation(TreeNodePid.class) != null) {
                    f.setAccessible(true);
                    pid = f.getInt(t);
                }
                if (f.getAnnotation(TreeNodeLabel.class) != null) {
                    f.setAccessible(true);
                    label = (String) f.get(t);
                }
            }
            node = new Node(id, pid, label);
            nodes.add(node);
        }

        Log.e("TAG", nodes + "");

        /**
         * 设置Node间的节点关系
         */
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);

            for (int j = i + 1; j < nodes.size(); j++) {
                Node m = nodes.get(j);

                if (m.getParentId() == n.getId()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId() == n.getParentId()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }

        for (Node n : nodes) {
            setNodeIcon(n);
        }
        return nodes;
    }

    public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        List<Node> result = new ArrayList<>();
        List<Node> nodes = datasToNodes(datas);
        // 获得树的根结点
        List<Node> rootNodes = getRootNodes(nodes);

        for (Node node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }

        Log.e("TAG", result.size() + "");
        return result;
    }

    /**
     * 把一个节点的所有孩子节点都放入result
     *
     * @param result
     * @param node
     * @param defaultExpandLevel
     */
    private static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
        result.add(node);
        if (defaultExpandLevel >= currentLevel) {
            node.setExpand(true);
        }
        if (node.isLeaf())
            return;

        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(result, node.getChildren().get(i), defaultExpandLevel,currentLevel + 1);
        }

    }

    /**
     * 过滤出可见的节点
     *
     * @param nodes
     * @return
     */
    public static List<Node> filterVisibleNodes(List<Node> nodes) {
        List<Node> result = new ArrayList<Node>();

        for (Node node : nodes) {
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 从所有节点中过滤出根节点
     *
     * @param nodes
     * @return
     */
    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node.isRoot()) {
                root.add(node);
            }
        }
        return root;
    }

    /**
     * 为Node设置图标
     *
     * @param n
     */
    private static void setNodeIcon(Node n) {
        if (n.getChildren().size() > 0 && n.isExpand()) {
            n.setIcon(R.drawable.tree_ex);
        } else if (n.getChildren().size() > 0 && !n.isExpand()) {
            n.setIcon(R.drawable.tree_ec);
        } else {
            n.setIcon(-1);
        }
    }

}
