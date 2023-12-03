package com.lazzy.base.algorithm.pathfinding;

import lombok.Data;

/**
 * 寻址算法节点类
 *
 */
@Data
public class Node {
    /** x、y轴坐标 **/
    private int x, y;
    /** f = g + h g是起始点距离 h是终点距离 f是最短路径 **/
    private int f, g, h;

    private Node parent;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
