package org.rssb.phonetree.common;

@FunctionalInterface
public interface ResponseHandler {
    Response handlerResponse(ContextHolder contextHolder);
}
