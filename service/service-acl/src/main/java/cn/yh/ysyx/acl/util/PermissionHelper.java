package cn.yh.ysyx.acl.util;

import cn.yh.ysyx.model.acl.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PermissionHelper {
    
    /**
     * 构建树形菜单
     * @param treeNodes 所有节点(菜单)
     * @return List<Permission> 
     * @throws 
     */
    public static List<Permission> build(List<Permission> treeNodes) {
        // 树形菜单
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if (treeNode.getPid() == 0) { // 找到根节点（pid=0）
                treeNode.setLevel(1);     // 设置根节点层级为1
                trees.add(findChildren(treeNode, treeNodes)); // 递归构建子树
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点，构建子树
     * @param treeNode 父节点
     * @param treeNodes 所有节点
     * @return Permission 
     * @throws 
     */
    private static Permission findChildren(Permission treeNode, List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<>()); // 初始化子节点列表

        for (Permission node : treeNodes) {
            if (node.getPid().equals(treeNode.getId())) { // 找到当前节点的直接子节点
                node.setLevel(treeNode.getLevel() + 1);   // 子节点层级 = 父层级+1
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                // 递归：为子节点继续找它的子节点
                treeNode.getChildren().add(findChildren(node, treeNodes));
            }
        }
        return treeNode;
    }
}
