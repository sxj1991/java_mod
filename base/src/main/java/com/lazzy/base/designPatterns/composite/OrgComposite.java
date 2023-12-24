package com.lazzy.base.designPatterns.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式（Composite Pattern）是一种结构型设计模式，它允许将对象组合成树形结构以表示部分-整体的层次结构。
 * 组合模式使得客户端对单个对象和组合对象的使用具有一致性。
 * <p>
 * 组件
 * 组合模式主要包括以下几个组件：
 * <p>
 * 组件（Component）：为所有对象定义一个接口，在适当情况下，实现所有类共有接口的默认行为。声明一个接口用于访问和管理子部件。
 * <p>
 * 叶子（Leaf）：在组合中表示叶节点对象，叶节点没有子节点。
 * <p>
 * 组合（Composite）：定义有子部件的那些部件的行为。存储子部件。实现在组件接口中与子部件有关的操作。
 */
public class OrgComposite {
    /** org 组织可以看成是总体 */
    private static class Org{
        private String orgName;
        private Integer orgStaffNum;
        /** child org 可以看成总体一部分 */
        private final List<Org> childOrg = new ArrayList<>();

        public Org(String orgName, Integer orgStaffNum) {
            this.orgName = orgName;
            this.orgStaffNum = orgStaffNum;
        }

        public void printOrgInfo(Org org){
            System.out.println(org.orgName+":"+org.orgStaffNum);
            if(!org.childOrg.isEmpty()){
                org.childOrg.forEach(this::printOrgInfo);
            }
        }
    }

    public static void main(String[] args) {
        Org org = new Org("公司", 200);
        Org child1 = new Org("子公司1",100);
        Org child2 = new Org("子公司2",100);
        org.childOrg.add(child1);
        org.childOrg.add(child2);
        org.printOrgInfo(org);
    }

}
