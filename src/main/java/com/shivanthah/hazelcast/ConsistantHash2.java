package com.shivanthah.hazelcast;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

public class ConsistantHash2 {

    private TreeMap<Long, Node> circle = new TreeMap<Long, Node>();

    private List<Node> realNodes = new ArrayList<Node>();

    public void addNode(Node node){
        realNodes.add(node);

        Long nodeKey = md5(node.getNodeNum());
        System.out.println(node.getNodeNum() + " md5:" + nodeKey);
        circle.put(nodeKey, node);
    }

    public void removeNode(Node node){
        realNodes.remove(node);

        Long nodeKey = md5(node.getNodeNum());
        circle.remove(nodeKey);
    }

    public Node getNode(String key){
        Long keyMd5 = md5(key);
        SortedMap<Long, Node> sortedMap = circle.tailMap(keyMd5);
        Long k = null;

        if(sortedMap.isEmpty()){
            k = (circle.firstKey());
            System.out.println("not hit");
        }else{
            k = (sortedMap.firstKey());
        }
        Node node = circle.get(k);

        System.out.println(key + "(" + keyMd5 +") --->" + node.toString() + "(" + md5(node.toString()) + ")");
        return node;
    }

    private long md5(String key){
        byte[] bKey = DigestUtils.md5(key.getBytes());
        long res = ((long) (bKey[3] & 0xFF) <<24) | ((long) (bKey[2] & 0xFF) <<16) | ((long) (bKey[1] & 0xFF) <<8)| bKey[0] & 0xFF;
        return res;
    }

    static class Node{
        private String nodeNum;

        public Node(String num){
            this.nodeNum = num;
        }

        public String getNodeNum() {
            return nodeNum;
        }
    }
}