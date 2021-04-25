package com.forever.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author WJX
 * @date 2021/4/8 16:57
 *
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * 示例 2:
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 *
 * 示例 3:
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * 示例 4:
 * 输入: s = ""
 * 输出: 0
 *
 *
 * 解题思路:滑动窗口
 */
public class SlidingWindow {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("acvda"));
    }


    private static int lengthOfLongestSubstring(String s) {

        int moveIndex = 0,maxLength = 0;
        char[] chars = s.toCharArray();
        Set<Character> characterSet = new HashSet<>();

        for (int i = 0; i < chars.length; i++) {

            if (i > 0) {
                characterSet.remove(chars[i - 1]);
            }

            for (;moveIndex < chars.length && (!characterSet.contains(chars[moveIndex]));) {
                characterSet.add(chars[moveIndex++]);
            }

            maxLength = Math.max(maxLength, moveIndex - i);
        }

        return maxLength;
    }



}
