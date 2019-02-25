package com.minmai.wallet.moudles.bean.response;

import java.util.List;

/**
 * 留言数据
 */
public class LeavingMsg {

    private String pageNo;

    private String pageSize;

    private String count;

    private String currentPage;

    private String totalCount;

    private String numPerPage;

    private String firstResult;

    private String maxResults;

    private String pageCurrent;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(String pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    private List<ListLeaving> list;

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(String numPerPage) {
        this.numPerPage = numPerPage;
    }

    public String getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(String firstResult) {
        this.firstResult = firstResult;
    }

    public String getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(String maxResults) {
        this.maxResults = maxResults;
    }

    public List<ListLeaving> getList() {
        return list;
    }

    public void setList(List<ListLeaving> list) {
        this.list = list;
    }

}
