package org.rssb.phonetree.common;

@FunctionalInterface
public interface Delegator {
    void delegate(ContextHolder contextHolder);
}
