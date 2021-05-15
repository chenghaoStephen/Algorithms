package com.algorithms.subject.blankcase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 给出一组立方体箱子，长宽高，如 10 8 6（长>宽>高）
 * 对于箱子a和b，如果a的长宽高均大于b的长宽高，则a可以嵌套b
 * 不考虑一个箱子内部并行嵌套多个箱子的情况，只嵌套一个
 * 问：嵌套完成后，最少能看到多少个箱子
 */
public class CalcMinNum {

    public static int size = 40;
    public static boolean[][] mem = new boolean[size][size];
    public static boolean[] visit = new boolean[size];
    public static int[] link = new int[size];

    static {
        for (int i = 0; i < link.length; i++) {
            link[i] = -1;
        }
    }

    public static void main(String[] args) {
        // 随机生成数据
        List<Case> caseList = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 40; i++) {
//            int l = random.nextInt(1000) + 5;
//            int w = random.nextInt(l - 4) + 3;
//            int h = random.nextInt(w - 2) + 1;
//            Case cn = new Case(l, w, h);
//            caseList.add(cn);
//        }
//        // list写入文件
//        try (
//            FileWriter fo = new FileWriter("E://data.txt");
//            BufferedWriter bo = new BufferedWriter(fo);
//        ) {
//            for (Case item : caseList) {
//                bo.write(item.getL() + "," + item.getW() + "," +item.getH());
//                bo.newLine();
//            }
//            bo.flush();
//        } catch (Exception e){
//
//        }
        try (
            FileReader fr = new FileReader("E://data.txt");
            BufferedReader bw = new BufferedReader(fr);
        ) {
            String line = null;
            while ((line = bw.readLine()) != null) {
                String[] nums = line.split(",");
                Case c = new Case(Integer.valueOf(nums[0]), Integer.valueOf(nums[1]), Integer.valueOf(nums[2]));
                caseList.add(c);
            }
        } catch (Exception e) {

        }

        // 按长-小到大排序，后面的箱子可能能嵌套前面的箱子
        caseList.sort((Case c1, Case c2) -> {
            return c1.getL() - c2.getL();
        });
        // 二维数组保存箱子的包含关系，(i,j)为1表示i可以嵌套j
        for (int i = 1; i < size; i++) {
            for (int j = 0; j < i; j++) {
                if (caseList.get(j).isSmallerThan(caseList.get(i))) {
                    mem[i][j] = true;
                }
            }
        }
        System.out.println(getMinNum2());
    }

    /**
     * 二分图最少路径匹配 = 节点数量 - 最大覆盖
     * @return
     */
    private static int getMinNum2() {
        int res = 0;
        for (int i = 0; i < size; i++) {
            visit = new boolean[size];
            if (find(i)) {
                res++;
            }
        }
        return size - res;
    }
    private static boolean find(int i) {
        for (int j = 0; j < size; j++) {
            if (mem[i][j] && !visit[j]) {
                visit[j] = true;
                if (link[j] == -1 || find(link[j])) {
                    link[j] = i;
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 暴力解法，能返回所有最优结果，时间复杂度高
     * @return
     */
    private static int getMinNum() {
        // 记录每组箱子最外层的箱子的 index
        Set<Set<Integer>> res = new HashSet<>();
        // 新增一个空箱子
        Set<Integer> sub = new HashSet<>();
        res.add(sub);
        // 遍历所有箱子（按长从小到大）
        for (int ci = 0; ci < mem.length; ci++) {
            // 嵌套在已存在的箱子外层
            Set<Set<Integer>> tmp = new HashSet<>(res);
            boolean hasIns = false;
            Set<Set<Integer>> extS = new HashSet<>();
            for (Set<Integer> inner : tmp) {
                boolean isIns = false;
                for (Integer c : inner) {
                    if (mem[ci][c]) {
                        // 能嵌套，则插入一组
                        Set<Integer> ins = new HashSet<>(inner);
                        ins.remove(c);
                        ins.add(ci);
                        res.add(ins);
                        isIns = true;
                        hasIns = true;
                    }
                }
                if (!isIns) {
                    // 均未能嵌套，加入新箱子
                    Set<Integer> ins = new HashSet<>(inner);
                    ins.add(ci);
                    res.add(ins);
                    extS.add(ins);
                }
                // 删除原来的
                res.remove(inner);
            }
            // 删除需要更多箱子的组
            if (hasIns && extS.size() > 0) {
                extS.forEach(item -> {
                    res.remove(item);
                });
            }
        }
        return res.iterator().next().size();
    }

}
