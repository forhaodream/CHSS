package com.sheepshop.businessside.okhttp;

/**
 * SSH系统 json 接口统一模型
 * @param <T>
 */
public class SSHBaseModel<T> {

    /**接口状态码, 该代码不代表服务器状态码*/
    private int Code;

    /**消息,接口返回的消息内容*/
    private String Msg;

    /**主体, 相应内容 老版本使用的*/
    private T Response;

    /**主体, 新版本和SpringBoot 使用的*/
    private T content;

    /** 是否为第一页 */
    private boolean first;

    /** 是否为第后页 */
    private boolean last;

    /** 总页数 */
    private int totalPages;

    /** 总条目数 */
    private int totalElements;

    /** 当前页数 */
    private int number;

    /** 页码大小 */
    private int size;

    /** 排序内容 */
    private String[] sort;

    /** 本页条目数 */
    private int numberOfElements;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public T getResponse() {
        return Response;
    }

    public void setResponse(T response) {
        Response = response;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String[] getSort() {
        return sort;
    }

    public void setSort(String[] sort) {
        this.sort = sort;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
}
