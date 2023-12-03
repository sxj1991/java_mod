package com.lazzy.base.algorithm.pathfinding;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 算法demo 采用简单的16 * 16 节点
 * grid 模拟障碍物
 * A * 算法通过比较移动节点路径距离长短，优先移动节点找到最短距离。
 */
@Slf4j
public class AStarAlgorithm {
    /**
     * 模拟地图格子长宽大小
     */
    final static int SIZE = 16;
    /**
     * 地图，0表示可以通过的格子，1表示障碍物
     **/
    static int[][] grid = new int[SIZE][SIZE];
    /**
     * 方向数组，用于移动到相邻的格子
     **/
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    /**
     * 判断坐标轴是否超出边界
     */
    static boolean isValid(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    /**
     * 是否到达终点
     */
    static boolean isDestination(int x, int y, Node dest) {
        return x == dest.getX() && y == dest.getY();
    }

    /**
     * 对角线距离计算距离估值
     * 可以水平垂直移动，同时可以对角线移动
     */
    static int calculateHValue(int x, int y, Node dest) {
        // 曼哈顿距离 适合网格移动 不能对角线移动 Math.abs(x - dest.x) + Math.abs(y - dest.y);
        return Math.max(Math.abs(x - dest.getX()), Math.abs(y - dest.getY()));
    }

    /**
     * 回溯节点信息 头插法单链结构
     */
    static void tracePath(Node node) {
        if (node == null) {
            return;
        }
        tracePath(node.getParent());
        log.info("(" + node.getX() + ", " + node.getY() + ")");
    }

    /**
     * AStar寻路算法
     **/
    static void aStarSearch(Node src, Node dest) {
        // 校验起始点和终点不是无效节点
        if (grid[src.getX()][src.getY()] == 1 || grid[dest.getX()][dest.getY()] == 1) {
            System.out.println("起点或终点为障碍物，无法找到路径");
            return;
        }
        // 节点是否已被路径标注
        boolean[][] closedList = new boolean[SIZE][SIZE];
        // 优先队列 堆排序
        // 节点排序(差值越小越靠前，即到达终点时，x、y均为0 该节点差值最小)
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getF));

        // 初始化起始节点距离信息
        src.setG(0);
        src.setH(calculateHValue(src.getX(), src.getY(), dest));
        src.setF(src.getG()+src.getH());

        // 放入起始节点
        pq.offer(src);

        while (!pq.isEmpty()) {
            // 取出节点
            Node currentNode = pq.poll();

            // 当前节点已标注
            closedList[currentNode.getX()][currentNode.getY()] = true;
            // 节点移动是否到达终点，是开始回溯节点信息
            // 其中因节点探明的路线只能走一次，所以只会有一个节点到达终点
            if (isDestination(currentNode.getX(), currentNode.getY(), dest)) {
                log.info("找到路径!开始回溯节点信息。");
                tracePath(currentNode);
                return;
            }

            // 移动节点 上下左右移动
            for (int i = 0; i < 4; i++) {
                int newX = currentNode.getX() + dx[i];
                int newY = currentNode.getY() + dy[i];

                if (isValid(newX, newY) && grid[newX][newY] != 1 && !closedList[newX][newY]) {
                    int gNew = currentNode.getG() + 1;
                    int hNew = calculateHValue(newX, newY, dest);
                    int fNew = gNew + hNew;

                    Node newNode = new Node(newX, newY);
                    newNode.setG(gNew);
                    newNode.setH(hNew);
                    newNode.setF(fNew);
                    // 头插法 存入路径信息
                    newNode.setParent(currentNode);
                    // 存入移动节点
                    pq.offer(newNode);
                }
            }
        }

        log.warn("无法找到路径");
    }

public static void main(String[] args) {
        Node src = new Node(1, 0);
        Node dest = new Node(2, 1);
        // 在这里设置障碍物
        // 初始化障碍物
        grid[1][1] = 1;
        grid[2][0] = 1;
        aStarSearch(src, dest);
    }
}