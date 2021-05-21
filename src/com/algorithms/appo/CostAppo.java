package com.algorithms.appo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CostAppo {

    public static final BigDecimal zero = new BigDecimal("0");

    public static void main(String[] args) {
//        // 分摊总金额
//        BigDecimal sem = new BigDecimal("10000000000.00");
//        // 生成项目
//        Random monRandom = new Random();
//        Random limRandom = new Random();
//        List<Project> projectList = new ArrayList<>();
//        for (int i = 0; i < 1000000; i++) {
//            Project project = new Project();
//            project.manMon = new BigDecimal(monRandom.nextInt(100) + 100);
//            int seed = limRandom.nextInt(100);
//            if (seed >= 45 && seed < 55) {
//                project.limit = new BigDecimal(5000 + seed * 100);
//            }
//            project.reAmount = new BigDecimal("0"); // 实摊金额初始化为0
//            projectList.add(project);
//        }
//
//        long start1 = System.currentTimeMillis();
//        appoFunc2(sem, projectList);
//        long end1 = System.currentTimeMillis();
//        System.out.println("耗时：" + (end1 - start1) + "ms");


        // 分摊总金额
        BigDecimal sem = new BigDecimal("10000000000.00");
        // 生成项目
        Random monRandom = new Random();
        Random limRandom = new Random();
        List<Project> projectList = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            Project project = new Project();
            project.setManMon(new BigDecimal(monRandom.nextInt(100) + 100));
            int seed = limRandom.nextInt(100);
            if ((seed >= 5 && seed < 15) || (seed >= 45 && seed < 55) || (seed >= 85 && seed < 95)) {
                // 为一部分值设置上限
                project.setLimit(new BigDecimal(500 + seed * 10));
            }
            project.setReAmount(new BigDecimal("0")); // 实摊金额初始化为0
            projectList.add(project);
        }

        // 计算方案一的耗时
        long start1 = System.currentTimeMillis();
        appoFunc1(sem, projectList);
        long end1 = System.currentTimeMillis();
        System.out.println("方案一耗时：" + (end1 - start1) + "ms");
        BigDecimal sum1 = new BigDecimal("0");
        List<Project> exceedList1 = new ArrayList<>();
        for (Project project :  projectList) {
            sum1 = sum1.add(project.getReAmount());
            if (project.getLimit() != null && project.getReAmount().compareTo(project.getLimit()) >= 0) {
                exceedList1.add(project);
            }
        }
//        System.out.println(projectList);
        System.out.println("总和为：" + sum1.setScale(2, BigDecimal.ROUND_HALF_EVEN));
        System.out.println("达到上限项目数量：" + exceedList1.size());
//        System.out.println(exceedList1);

        // 清空数据
        for (Project project :  projectList) {
            project.setShAmount(new BigDecimal("0"));
            project.setReAmount(new BigDecimal("0"));
        }

        // 计算方案二的耗时
        long start2 = System.currentTimeMillis();
        appoFunc2(sem, projectList);
        long end2 = System.currentTimeMillis();
        System.out.println("方案二耗时：" + (end2 - start2) + "ms");
        BigDecimal sum2 = new BigDecimal("0");
        List<Project> exceedList2 = new ArrayList<>();
        for (Project project :  projectList) {
            sum2 = sum2.add(project.getReAmount());
            if (project.getLimit() != null && project.getReAmount().compareTo(project.getLimit()) >= 0) {
                exceedList2.add(project);
            }
        }
//        System.out.println(projectList);
        System.out.println("总和为：" + sum2.setScale(2, BigDecimal.ROUND_HALF_EVEN));
        System.out.println("达到上限项目数量：" + exceedList2.size());
//        System.out.println(exceedList2);
    }

    /**
     * 方案一，采用递归分摊
     * 第一次分摊后，记录超过上限的项目和超出金额，下一次将超出金额分摊给剩余的项目
     * 重复此动作，直到没有超出上限的项目为止
     * @param sem 分摊总金额
     * @param projectList 项目数据
     */
    private static void appoFunc1(BigDecimal sem, List<Project> projectList) {
        // 如果金额为0，或者没有未达上限的项目
        if (sem.compareTo(zero) == 0 || projectList == null || projectList.size() == 0) {
            return;
        }
        System.out.println("分摊次数+1");
        // 计算总有效人月
        BigDecimal sn = new BigDecimal(0);
        for (Project project : projectList) {
            sn = sn.add(project.getManMon());
        }
        // 根据有效人月比例计算分摊金额，如果超出上限，按上限分摊；如果未超出，按计算结果分摊
        List<Project> unExceedList = new ArrayList<>(); // 未超出上限列表，进行下一次递归用
        BigDecimal exceedSum = new BigDecimal(0);   // 超出金额总和
        for (Project project : projectList) {
            project.setShAmount((sem.multiply(project.getManMon())).divide(sn, 10, BigDecimal.ROUND_HALF_EVEN));
            // 已分摊 + 本次分摊
            BigDecimal amount = project.getReAmount().add(project.getShAmount());
            // 存在上限的，判断是否超出上限
            if (project.getLimit() != null && amount.compareTo(project.getLimit()) >= 0) {
                // 超出上限，实际分摊为上限值，记录超出金额
                project.setReAmount(project.getLimit());
                exceedSum = exceedSum.add(amount.subtract(project.getLimit()));
            } else {
                // 未超上限，实际分摊为计算结果，加入下一次分摊列表
                project.setReAmount(amount);
                unExceedList.add(project);
            }
        }
        // 递归进行下一次分摊
        appoFunc1(exceedSum, unExceedList);
    }

    /**
     * 方案二，对有金额上限的项目，根据 上限金额/有效人月 从小到大进行排列
     * 遍历，根据有效人月比计算应摊金额，判断是否超过上限
     * 如果超出，实摊金额为上限金额，总金额、总人月减出该项目，继续后面项目的计算
     * 如果未超出，则后面的项目必然都不会超出，从该项目向后均按人月比例分摊
     * @param sem 分摊总金额
     * @param projectList 项目数据
     */
    private static void appoFunc2(BigDecimal sem, List<Project> projectList) {
        // 计算总有效人月
        BigDecimal sn = new BigDecimal(0);
        for (Project project : projectList) {
            sn = sn.add(project.getManMon());
        }
        // 计算 单价 = 上限金额/有效人月
        List<Project> limList = new ArrayList<>();   // 存在上限的项目
        List<Project> unLimList = new ArrayList<>(); // 不存在上限的项目
        for (Project project : projectList) {
            if (project.getLimit() != null) {
                project.setUnitLimit(project.getLimit().divide(project.getManMon(), 2, BigDecimal.ROUND_HALF_EVEN));
                limList.add(project);
            } else {
                unLimList.add(project);
            }
        }
        // 存在上限的项目，按照单价从小到大排序
        limList.sort((a, b) -> a.getUnitLimit().compareTo(b.getUnitLimit()));
        // 没有上限的项目放在后面
        limList.addAll(unLimList);
        // 该变量记录当前项目是否超出上限，如果未超出，后面的项目必然不会超出
        boolean isExceed = true;
        // 按照单价从小到大进行分摊
        for (Project project : limList) {
            project.setShAmount((sem.multiply(project.getManMon())).divide(sn, 10, BigDecimal.ROUND_HALF_EVEN));
            // 存在上限的，判断是否超出上限
            if (isExceed) {
                if (project.getLimit() != null && project.getShAmount().compareTo(project.getLimit()) >= 0) {
                    project.setReAmount(project.getLimit());
                    // 总金额、总人月减出该项目，继续后面项目的计算
                    sn = sn.subtract(project.getManMon());
                    sem = sem.subtract(project.getLimit());
                } else {
                    project.setReAmount(project.getShAmount());
                    isExceed = false; // 未超出上限，后面的项目也不会超出上限
                }
            } else {
                project.setReAmount(project.getShAmount());
            }
        }
    }

}
