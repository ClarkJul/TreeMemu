package com.clark.treemenu.bean;

import com.clark.treemenu.annotation.TreeNodeId;
import com.clark.treemenu.annotation.TreeNodeLabel;
import com.clark.treemenu.annotation.TreeNodePid;

public class OrgBean {
    @TreeNodeId
    private int _id;
    @TreeNodePid
    private int parentId;
    @TreeNodeLabel
    private String name;


    public OrgBean(int _id, int parentId, String name)
    {
        this._id = _id;
        this.parentId = parentId;
        this.name = name;
    }

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public int getParentId()
    {
        return parentId;
    }

    public void setParentId(int parentId)
    {
        this.parentId = parentId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
