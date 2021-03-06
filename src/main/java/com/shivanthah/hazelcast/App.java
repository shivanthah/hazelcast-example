/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.shivanthah.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.*;

public class App {

    public static void main(String[] args) {
        ConsistantHash2 h = new ConsistantHash2();

        Config config = new Config();
        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(config);
        MembershipListener listener = new MembershipListener() {
            @Override
            public void memberAdded(MembershipEvent membershipEvent) {
                h.addNode(new ConsistantHash2.Node(membershipEvent.getMember().getUuid()));
            }

            @Override
            public void memberRemoved(MembershipEvent membershipEvent) {
                h.removeNode(new ConsistantHash2.Node(membershipEvent.getMember().getUuid()));
            }

            @Override
            public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {
            }
        };
        hzInstance.getCluster().addMembershipListener(listener);
        HazelcastInstance hzInstance2 = Hazelcast.newHazelcastInstance(config);
        HazelcastInstance hzInstance3 = Hazelcast.newHazelcastInstance(config);


        ConsistantHash2.Node abc = h.getNode("abc");
        System.out.println(hzInstance.getCluster().getMembers().stream().filter(member -> member.getUuid().equalsIgnoreCase(abc.getNodeNum())).findFirst().get());

        ConsistantHash2.Node abc1 = h.getNode("1121");
        System.out.println(hzInstance.getCluster().getMembers().stream().filter(member -> member.getUuid().equalsIgnoreCase(abc1.getNodeNum())).findFirst().get());

        ConsistantHash2.Node abc2 = h.getNode("211212");
        System.out.println(hzInstance.getCluster().getMembers().stream().filter(member -> member.getUuid().equalsIgnoreCase(abc2.getNodeNum())).findFirst().get());

        ConsistantHash2.Node abc3 = h.getNode("wewew");
        System.out.println(hzInstance.getCluster().getMembers().stream().filter(member -> member.getUuid().equalsIgnoreCase(abc3.getNodeNum())).findFirst().get());

    }
}
