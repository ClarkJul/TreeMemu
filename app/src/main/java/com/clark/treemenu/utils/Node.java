package com.clark.treemenu.utils;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private int id;
    /**
     * 跟节点的parentId=0
     */
    private int parentId = 0;
    private String name;
    /**
     * 树的层级
     */
    private int level;
    /**
     * 是否是展开
     */
    private boolean isExpand = false;

    /**
     * 显示的图标：展开和闭合
     */
    private int icon;

    /**
     * 父节点
     */
    private Node parent;

    /**
     * 子节点
     */
    private List<Node> children = new ArrayList<Node>();


    public Node()
    {
    }

    public Node(int id, int pId, String name)
    {
        this.id = id;
        this.parentId = pId;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
//        return level;

        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;

        //递归设置子节点的展开状态
        if (!isExpand) {
            for (Node node : children) {
                node.setExpand(false);
            }
        }
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }


    /**
     * 判断是否为根节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断当前父节点的收缩状态
     *
     * @return
     */
    public boolean isParentExpand() {
        if (parent == null)
            return false;
        return parent.isExpand();
    }

    /**
     * 是否是叶子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    @Override
    public String toString()
    {
        return "Node [id=" + id + ", parentId=" + parentId + ", name=" + name
                + ", level=" + level + ", isExpand=" + isExpand + ", icon="
                + icon + "]";
    }
}
