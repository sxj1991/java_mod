package com.lazzy.base.algorithm.pathfinding;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author mrs.sun
 * 路径寻址广度优先算法
 */
@Slf4j
public class ShortestPathBFS {
    /**
     * 定义方向数组，分别表示上、下、左、右四个方向移动
     */
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    /**
     * 判断节点是否超出范围
     */
    public static boolean isValid(int x, int y, boolean[][] visited) {
        return x >= 0 && x < 16 && y >= 0 && y < 16 && !visited[x][y];
    }

    public static void findShortestPath(boolean[][] grid, Node start, Node end) {
        Queue<Node> queue = new LinkedList<>();
        // 已探索标记
        boolean[][] visited = new boolean[16][16];

        queue.add(start);
        visited[start.getX()][start.getY()] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.getX() == end.getX() && current.getY() == end.getY()) {
                // 到达终点 回溯路径信息
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.getParent();
                }
                Collections.reverse(path);
                // 打印节点信息
                log.info("找到路径!开始回溯节点信息。");
                path.forEach(node -> log.info("(" + node.getX() + ", " + node.getY() + ")"));
                return;
            }
            // 循环实现节点向4周运动探索并标记
            for (int i = 0; i < 4; i++) {
                int newX = current.getX() + dx[i];
                int newY = current.getY() + dy[i];

                if (isValid(newX, newY, visited) && !grid[newX][newY]) {
                    // 没超出界限队列添加新节点 继续探索
                    visited[newX][newY] = true;
                    Node next = new Node(newX, newY);
                    queue.add(next);
                    next.setParent(current);
                }
            }
        }
        // 没找到终点
        log.warn("无法找到路径");
    }

    public static void main(String[] args) {
        Node src = new Node(1, 0);
        Node dest = new Node(2, 1);

        // 初始化障碍物
        boolean[][] grid = new boolean[16][16];
        // 在这里设置障碍物
        grid[2][0] = true;
        findShortestPath(grid, src, dest);
    }
}