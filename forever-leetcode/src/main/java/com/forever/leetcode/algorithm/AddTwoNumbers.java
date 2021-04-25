package com.forever.leetcode.algorithm;

/**
 * @author WJX
 * @date 2021/4/25 10:56
 *
 * 两个 非空 的链表，表示两个非负的整数。
 * 它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储一位数字。
 *
 * 将两个数相加，并以相同形式返回一个表示和的链表。
 * 可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例 1：
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 *
 * 示例 2：
 * 输入：l1 = [0], l2 = [0]
 * 输出：[0]
 *
 * 示例 3：
 * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * 输出：[8,9,9,9,0,0,0,1]
 *
 *
 */
public class AddTwoNumbers {


    public static String testAddTwoNumbers() {
        ListNode listNode11 = new ListNode(9);
        ListNode listNode12 = new ListNode(9);
        ListNode listNode13 = new ListNode(9);
        ListNode listNode14 = new ListNode(9);
        ListNode listNode15 = new ListNode(9);
        ListNode listNode16 = new ListNode(9);
        ListNode listNode17 = new ListNode(9);

        ListNode listNode21 = new ListNode(9);
        ListNode listNode22 = new ListNode(9);
        ListNode listNode23 = new ListNode(9);
        ListNode listNode24 = new ListNode(9);

        listNode11.next = listNode12; listNode12.next = listNode13; listNode13.next = listNode14;
        listNode14.next = listNode15; listNode15.next = listNode16; listNode16.next = listNode17;
        listNode21.next = listNode22; listNode22.next = listNode23; listNode23.next = listNode24;

        StringBuilder stringBuilder = new StringBuilder("[");
        ListNode addNode1 = addTwoNumbers(listNode11, listNode21);

        while (true) {
            stringBuilder.append(addNode1.val).append(",");

            if (addNode1.hasNext()) {
                addNode1 = addNode1.next;
            } else {
                break;
            }
        }

        stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "]");

        return stringBuilder.toString();
    }


    private static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        int nextNum = (l1.val + l2.val) / 10;
        ListNode firstNode = new ListNode((l1.val + l2.val) % 10);

        ListNode lastNode = firstNode;
        while (l1.hasNext() || l2.hasNext() || nextNum > 0) {
            l1 = l1.getNextOrZero();
            l2 = l2.getNextOrZero();
            int num = l1.val + l2.val + nextNum;

            nextNum = num / 10;
            lastNode.next = new ListNode(num % 10);

            lastNode = lastNode.next;
        }

        return firstNode;
    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }

        boolean hasNext() { return next != null; }
        ListNode getNextOrZero() {
            return hasNext() ? next : new ListNode(0);
        }
    }

}
