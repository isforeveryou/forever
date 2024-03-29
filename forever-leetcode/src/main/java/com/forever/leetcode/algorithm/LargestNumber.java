package com.forever.leetcode.algorithm;

import java.util.Arrays;

/**
 * @author WJX
 * @date 2021/4/25 10:52
 *
 * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
 * 注意：输出结果可能非常大，所以需要返回一个字符串而不是整数。
 *  
 *
 * 示例 1：
 * 输入：nums = [10,2]
 * 输出："210"
 *
 * 示例 2：
 * 输入：nums = [3,30,34,5,9]
 * 输出："9534330"
 *
 * 示例 3：
 * 输入：nums = [1]
 * 输出："1"
 *
 * 示例 4：
 * 输入：nums = [10]
 * 输出："10"
 *
 */
public class LargestNumber {


    public static String largestNumber(int[] nums) {

        String[] strings = new String[nums.length];

        for (int i = 0; i < nums.length; i++) {
            strings[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(strings, (x, y) -> (x + y).compareTo((y + x)));

        for (int i = strings.length - 2; i >= 0; i--) {
            strings[strings.length - 1] = "0".equals(strings[strings.length - 1]) ? strings[i] : strings[strings.length - 1] + strings[i];
        }

        return strings[strings.length - 1];
    }


}
